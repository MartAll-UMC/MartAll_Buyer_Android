package com.org.martall.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemHomeProductBinding
import com.org.martall.models.Item
import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.services.ApiService
import com.org.martall.view.store.ProductDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class ProductSimpleRVAdapter(
    private val itemList: List<Item>,
    private val api: ApiService,
) :
    RecyclerView.Adapter<ProductSimpleRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
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
    override fun getItemId(position: Int): Long {
        return itemList[position].itemId.toLong()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: ItemHomeProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        init {
//            binding.root.setOnClickListener {
//                val idx = adapterPosition
//                val intent = Intent(binding.root.context, ProductDetailActivity::class.java)
//                Log.d(
//                    "[PRINT/HOME]",
//                    "${itemList[idx].itemId} ${itemList[idx].toString()}"
//                )
//                intent.putExtra(
//                    ProductDetailActivity.EXTRA_MART_ID,
//                    martNameToId(itemList[idx].martShopName)
//                )
//                intent.putExtra(ProductDetailActivity.EXTRA_ITEM_ID, itemList[idx].itemId)
//                binding.root.context.startActivity(intent)
//            }
//        }

        fun bind(item: Item) {
            binding.apply {
                Glide.with(itemView).load(item.pic).into(productImgIv)
                productNameTv.text = item.itemName
                val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
                productPriceTv.text = "${formattedPrice}원"


                productImgIv.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, ProductDetailActivity::class.java).apply {
                        putExtra(ProductDetailActivity.EXTRA_ITEM_ID, itemId)
                    }
                    context.startActivity(intent)
                }

                productNameTv.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, ProductDetailActivity::class.java).apply {
                        putExtra(ProductDetailActivity.EXTRA_ITEM_ID, itemId)
                    }
                    context.startActivity(intent)
                }
            }



        // 초기 상태 설정
            updateLikeButton(item.like)

            binding.productLikeIb.setOnClickListener {
                val itemId = item.itemId
                val isLiked = !item.like

                // 클릭 상태를 업데이트하여 UI를 변경
                item.like = isLiked
                updateLikeButton(isLiked)

                if (item.like) {
                    api.likedItem(item.itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                        override fun onResponse(
                            call: Call<ItemLikedResponseDTO>,
                            response: Response<ItemLikedResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                                Log.d("Retrofit", "Update request successful for item $itemId")
                            } else {
                                Log.e("Retrofit", "Failed to send update request for item $itemId")
                            }
                        }

                        override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                            // 실패 시 처리: 클릭 상태를 이전 상태로 변경하고 실패 메시지 출력
                            item.like = !isLiked
                            updateLikeButton(!isLiked)
                            Log.e(
                                "ProductSimpleRVAdapter",
                                "Failed to send update request for item $itemId"
                            )
                        }
                    })
                } else {
                    api.unLikedItem(item.itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                        override fun onResponse(
                            call: Call<ItemLikedResponseDTO>,
                            response: Response<ItemLikedResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                                Log.d("Retrofit", "Update request successful for item $itemId")
                            } else {
                                Log.e("Retrofit", "Failed to send update request for item $itemId")
                            }
                        }

                        override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                            // 실패 시 처리: 클릭 상태를 이전 상태로 변경하고 실패 메시지 출력
                            item.like = !isLiked
                            updateLikeButton(!isLiked)
                            Log.e(
                                "ProductSimpleRVAdapter",
                                "Failed to send update request for item $itemId"
                            )
                        }
                    })
                }
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            val likeIconResId =
                if (isLiked) R.drawable.ic_like_filled_20dp else R.drawable.ic_heart_line_20dp
            binding.productLikeIb.setImageResource(likeIconResId)
        }
    }
}
