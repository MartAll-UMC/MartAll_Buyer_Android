package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.interfaces.MartItemdibs
import com.org.martall.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class CategoryRVAdapter(private var itemList: List<Item>, private val martItemdibs: MartItemdibs) :
    RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemCategoryProductBinding = ItemCategoryProductBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnLike.setOnClickListener {
                val idx = adapterPosition
                if (idx != RecyclerView.NO_POSITION) {
                    val item = itemList[idx]
                    val itemId = item.itemId
                    val isLiked = !item.isLiked

                    // 클릭 상태를 업데이트하여 UI를 변경
                    item.isLiked = isLiked
                    updateLikeButton(isLiked)

                    // 서버에 클릭 상태 업데이트 요청 보내기
                    martItemdibs.dibsItem(itemId, isLiked).enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (!response.isSuccessful) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                item.isLiked = !isLiked
                                updateLikeButton(!isLiked)
                                Log.e("Retrofit", "Failed to update like status for item: $itemId")
                            } else {
                                Log.d("Retrofit", "Like status updated successfully for item: $itemId")
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                            item.isLiked = !isLiked
                            updateLikeButton(!isLiked)
                            Log.e("Retrofit", "Failed to update like status for item: $itemId", t)
                        }
                    })
                }
            }
        }

        fun bind(item: Item) {
            binding.apply {
                tvProductName.text = item.itemName
                val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
                tvProductPrice.text = "${formattedPrice}원"
                // 초기 버튼 상태 설정
                updateLikeButton(item.isLiked)
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            if (isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_heart_line_20dp)
            }
        }
    }
}
