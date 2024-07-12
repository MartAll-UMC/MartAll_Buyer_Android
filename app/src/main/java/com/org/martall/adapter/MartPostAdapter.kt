package com.org.martall.adapter

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemMartPostBinding
import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.models.MartItemDTO
import com.org.martall.services.ApiService
//import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.view.mart.ProductDetailActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class MartPostAdapter(
    private val itemList: List<MartItemDTO>,
    private val martId: Int,
    private val api: ApiService) :

    RecyclerView.Adapter<MartPostAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(martId: Int, itemId: Int)
    }

    // 리스너 변수
    private var onItemClickListener: OnItemClickListener? = null

    // 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MartPostAdapter.ViewHolder {
        val binding: ItemMartPostBinding =
            ItemMartPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartPostAdapter.ViewHolder, position: Int) {
        Log.d("MartPostAdapter", "onBindViewHolder: position=$position")
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemMartPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isLiked: Boolean = false

        init {
            binding.root.setOnClickListener {
                val martId = martId
                val itemId = itemList[adapterPosition].itemId

                Log.d("MartPostAdapter", itemList[adapterPosition].likeYn.toString())

                val intent = Intent(binding.root.context, ProductDetailActivity::class.java)
                intent.putExtra(ProductDetailActivity.EXTRA_MART_ID, martId)
                intent.putExtra(ProductDetailActivity.EXTRA_ITEM_ID, itemId)
                Log.d(
                    "MartPostAdapter",
                    "martId: " + martId.toString() + "itemId: " + itemId.toString()
                )
                binding.root.context.startActivity(intent)
            }

            binding.itemLikeIc.setOnClickListener {
                Log.d("MartPostAdapter", itemList[adapterPosition].likeYn.toString())

                itemList[adapterPosition].likeYn = !itemList[adapterPosition].likeYn
                updateLikeUI(itemList[adapterPosition].likeYn)

                Log.d("MartPostAdapter", itemList[adapterPosition].likeYn.toString())

                Log.d("MartPostAdapter", itemList[adapterPosition].likeYn.toString())

                if (itemList[adapterPosition].likeYn) {
                    api.likedItem(itemList[adapterPosition].itemId)
                        .enqueue(object : Callback<ItemLikedResponseDTO> {
                            override fun onResponse(
                                call: Call<ItemLikedResponseDTO>,
                                response: Response<ItemLikedResponseDTO>,
                            ) {
                                if (!response.isSuccessful) {
                                    // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                } else {
                                    // 성공 시 로그로 상태 변경 확인
                                    Log.d(
                                        "MartPostAdapter",
                                        "Like status changed: " + itemList[adapterPosition].likeYn
                                    )
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                itemList[adapterPosition].likeYn = !itemList[adapterPosition].likeYn
                                updateLikeUI(itemList[adapterPosition].likeYn)
                            }
                        })
                } else {
                    api.unLikedItem(itemList[adapterPosition].itemId)
                        .enqueue(object : Callback<ItemLikedResponseDTO> {
                            override fun onResponse(
                                call: Call<ItemLikedResponseDTO>,
                                response: Response<ItemLikedResponseDTO>,
                            ) {
                                if (!response.isSuccessful) {
                                    // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                } else {
                                    // 성공 시 로그로 상태 변경 확인
                                    Log.d(
                                        "[MartPostAdapter]",
                                        "Like status changed: " + itemList[adapterPosition].likeYn
                                    )
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                itemList[adapterPosition].likeYn = !itemList[adapterPosition].likeYn
                                updateLikeUI(itemList[adapterPosition].likeYn)
                            }
                        })
                    }
            }


        }
        fun bind(item: MartItemDTO) {
            binding.itemNameTv.text = item.name
            val formattedPrice = NumberFormat.getNumberInstance().format(item.price)
            binding.itemPriceTv.text = "${formattedPrice}원"
            Log.d("MartPostAdapter", "Image URL: ${item.imageUrl}")
            Glide.with(itemView.context).load(item.imageUrl).into(binding.itemImgIc)

            Log.d("MartPostAdapter", item.likeYn.toString())
            // 초기 상태 설정
            updateLikeUI(item.likeYn)
        }

        private fun updateLikeUI(isLiked: Boolean) {
            // isLiked에 따라 UI 업데이트
            if (isLiked) binding.itemLikeIc.setImageResource(R.drawable.ic_like_filled_20dp)
            else binding.itemLikeIc.setImageResource(R.drawable.ic_like_unfilled_20dp)
        }
    }
}