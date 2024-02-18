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
//새로 나온 상품 어댑터 입니다. 카테고리와 리사이클러 뷰가 겹치나, api구조가 달라 새로 생성했습니다//
class SimpleProductRVAdapter(private val itemList: List<Item>, private val martItemdibs: MartItemdibs) :
    RecyclerView.Adapter<SimpleProductRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(private val binding: ItemCategoryProductBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnLike.setOnClickListener {
                val item = itemList[adapterPosition]
                val itemId = item.itemId

                // 클릭 상태를 업데이트하여 UI를 변경
                item.like = !item.like
                updateLikeButton(item.like)

                // 서버에 클릭 상태 업데이트 요청 보내기
                martItemdibs.dibsItem(itemId, item.like).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (!response.isSuccessful) {
                            // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                            item.like = !item.like
                            updateLikeButton(item.like)
                        } else {
                            // 성공 시 로그로 상태 변경 확인
                            Log.d("retrofit", "Like status changed: $item")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                        item.like = !item.like
                        updateLikeButton(item.like)
                    }
                })
            }
        }

        fun bind(item: Item) {
            binding.apply {
                tvProductName.text = item.itemName
                val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
                tvProductPrice.text = "${formattedPrice}원"
                // 초기 버튼 상태 설정
                updateLikeButton(item.like)
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