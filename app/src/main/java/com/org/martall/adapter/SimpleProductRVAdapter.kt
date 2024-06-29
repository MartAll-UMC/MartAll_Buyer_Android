package com.org.martall.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.models.Item
import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.services.ApiService
import com.org.martall.view.mart.ProductDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

//새로 나온 상품 어댑터 입니다. 카테고리와 리사이클러 뷰가 겹치나, api구조가 달라 새로 생성했습니다//
class SimpleProductRVAdapter(private val itemList: List<Item>, private val api: ApiService) :
    RecyclerView.Adapter<SimpleProductRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(private val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.likeBtn.setOnClickListener {
                val item = itemList[adapterPosition]
                val itemId = item.itemId

                // 클릭 상태를 업데이트하여 UI를 변경
                item.like = !item.like
                updateLikeButton(item.like)

                if (item.like) {
                    api.likedItem(itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                        override fun onResponse(
                            call: Call<ItemLikedResponseDTO>,
                            response: Response<ItemLikedResponseDTO>,
                        ) {
                            if (!response.isSuccessful) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                            } else {
                                // 성공 시 로그로 상태 변경 확인
                                Log.d("retrofit", "Like status changed: $item")
                            }
                        }

                        override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                            // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                            item.like = !item.like
                            updateLikeButton(item.like)
                        }
                    })
                } else {
                    api.unLikedItem(itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                        override fun onResponse(
                            call: Call<ItemLikedResponseDTO>,
                            response: Response<ItemLikedResponseDTO>,
                        ) {
                            if (!response.isSuccessful) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                            } else {
                                // 성공 시 로그로 상태 변경 확인
                                Log.d("retrofit", "Like status changed: $item")
                            }
                        }

                        override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                            // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                            item.like = !item.like
                            updateLikeButton(item.like)
                        }
                    })
                }
            }
        }

        fun bind(item: Item) {
            binding.apply {
                Glide.with(itemView).load(item.pic).into(itemImgIv)
                itemNameTv.text = item.itemName
                martNameTv.text = item.martShopName
                val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
                itemPriceTv.text = "${formattedPrice}원"
                // 초기 버튼 상태 설정
                updateLikeButton(item.like)

                itemImgIv.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, ProductDetailActivity::class.java).apply {
                        putExtra(ProductDetailActivity.EXTRA_ITEM_ID, itemId)
                    }
                    context.startActivity(intent)
                }

                itemNameTv.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, ProductDetailActivity::class.java).apply {
                        putExtra(ProductDetailActivity.EXTRA_ITEM_ID, itemId)
                    }
                    context.startActivity(intent)
                }
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            if (isLiked) {
                binding.likeBtn.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.likeBtn.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }
        }
    }
}