package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemHomeProductBinding
import com.org.martall.interfaces.MartItemdibs
import com.org.martall.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class ProductSimpleRVAdapter(private val itemList: List<Item>, private val martItemdibs: MartItemdibs) :
    RecyclerView.Adapter<ProductSimpleRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemHomeProductBinding = ItemHomeProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: ItemHomeProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.apply {
                productNameTv.text = item.itemName
                val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
                productPriceTv.text = "${formattedPrice}원"
            }

            // 초기 상태 설정
            updateLikeButton(item.like)

            binding.productLikeIb.setOnClickListener {
                val itemId = item.itemId
                val isLiked = !item.like

                // 클릭 상태를 업데이트하여 UI를 변경
                item.like = isLiked
                updateLikeButton(isLiked)

                // 로그 추가: 클릭 상태 변경 및 업데이트 요청 로그
                Log.d("ProductSimpleRVAdapter", "Item liked status changed: $isLiked")
                Log.d("ProductSimpleRVAdapter", "Sending update request for item $itemId with liked status: $isLiked")

                // 서버에 클릭 상태 업데이트 요청 보내기
                martItemdibs.dibsItem(itemId, isLiked).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                            Log.d("Retrofit", "Update request successful for item $itemId")
                        } else {
                            // 실패 시 처리: 클릭 상태를 이전 상태로 변경하고 실패 메시지 출력
                            item.like = !isLiked
                            updateLikeButton(!isLiked)
                            Log.e("Retrofit", "Failed to send update request for item $itemId")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        // 실패 시 처리: 클릭 상태를 이전 상태로 변경하고 실패 메시지 출력
                        item.like = !isLiked
                        updateLikeButton(!isLiked)
                        Log.e("ProductSimpleRVAdapter", "Failed to send update request for item $itemId")
                    }
                })
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            val likeIconResId =
                if (isLiked) R.drawable.ic_like_filled_20dp else R.drawable.ic_heart_line_20dp
            binding.productLikeIb.setImageResource(likeIconResId)
        }
    }
}
