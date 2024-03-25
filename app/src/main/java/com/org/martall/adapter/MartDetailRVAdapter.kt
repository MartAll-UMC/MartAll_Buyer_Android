package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.models.MartDataDTO
import com.org.martall.models.MartItemDTO
import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class MartDetailRVAdapter(
    private val martItem: MartDataDTO,
    private val api: ApiService) :
    RecyclerView.Adapter<MartDetailRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(martId: Int, itemId: Int)
    }

    // 리스너 변수
    private var onItemClickListener: OnItemClickListener? = null

    // 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MartDetailRVAdapter.ViewHolder {
        val binding =
            ItemCategoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartDetailRVAdapter.ViewHolder, position: Int) {
        holder.bind(martItem.items[position])
    }

    override fun getItemCount(): Int {
        return martItem.items.size
    }

    inner class ViewHolder(val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isLiked: Boolean = false

        init {
            binding.root.setOnClickListener {
                val martId = martItem.martId
                val itemId = martItem.items[adapterPosition].itemId

                // 리스너가 설정되어 있다면 실행
                onItemClickListener?.onItemClick(martId, itemId)
            }

            // 찜 기능
            binding.likeBtn.setOnClickListener {

                Log.d("[LIKE]", isLiked.toString())

                isLiked = !martItem.items[adapterPosition].isLike
                martItem.items[adapterPosition].isLike = isLiked
                updateLikeUI(martItem.items[adapterPosition].isLike)

                if(isLiked) {
                    api.likedItem(martItem.martId)
                        .enqueue(object : Callback<ItemLikedResponseDTO> {
                            override fun onResponse(
                                call: Call<ItemLikedResponseDTO>,
                                response: Response<ItemLikedResponseDTO>,
                            ) {
                                if (response.isSuccessful) {
                                    // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                                    Log.d("[LIKE]", "Update request successful for item $isLiked")
                                } else {
                                    Log.e("[LIKE]", "Failed to send update request for item $isLiked")
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                isLiked = !isLiked
                                martItem.items[adapterPosition].isLike = isLiked
                                updateLikeUI(isLiked)
                                Log.e(
                                    "[LIKE]",
                                    "Failed to send update request for item $itemId + $isLiked"
                                )
                            }
                        })
                } else {
                    api.unLikedItem(martItem.martId)
                        .enqueue(object : Callback<ItemLikedResponseDTO> {
                            override fun onResponse(
                                call: Call<ItemLikedResponseDTO>,
                                response: Response<ItemLikedResponseDTO>,
                            ) {
                                if (response.isSuccessful) {
                                    // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                                    Log.d("[LIKE]", "Update request successful for item $isLiked")
                                } else {
                                    Log.d("[LIKE]", "Update request successful for item $isLiked")
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                isLiked = !isLiked
                                martItem.items[adapterPosition].isLike = isLiked
                                updateLikeUI(isLiked)
                            }
                        })
                }
                }
        }

        fun bind(item: MartItemDTO) {
            binding.martNameTv.text = martItem.name
            binding.itemNameTv.text = item.name

            val formattedPrice = NumberFormat.getNumberInstance().format(item.price)
            binding.itemPriceTv.text = "${formattedPrice}원"
            if (item.imageUrl == null) {
                Log.d("[Image]", "null")
            }
            else {
                Log.d("mart_image", item.imageUrl)

                
            }
            Glide.with(itemView.context).load(item.imageUrl).into(binding.itemImgIv)

            updateLikeUI(item.isLike)
        }

        private fun toggleLikeState() {
            if (isLiked) {
                // 찜취소 -> UI 업데이트
                isLiked = !isLiked

//                // 찜취소 서버 통신
//                val apiService = ItemApiServiceManager.ItemapiService
//                val call =
//                    apiService.unLikedItem(itemId = martProduct.items[adapterPosition].itemId)
//
//                call.enqueue(object : Callback<ItemLikedResponseDTO> {
//                    override fun onResponse(
//                        call: Call<ItemLikedResponseDTO>,
//                        response: Response<ItemLikedResponseDTO>,
//                    ) {
//                        Log.d("isLiked", "찜 취소 서버 통신 성공")
//                    }
//
//                    override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
//                        Log.d("isLiked", "찜 취소 서버 통신 실패")
//                    }
//                })
            } else {
                isLiked = !isLiked
                // 찜하기 성공 -> UI 업데이트

//                // 찜하기 서버 통신
//                val apiService = ItemApiServiceManager.ItemapiService
//                val call = apiService.likedItem(itemId = martProduct.items[adapterPosition].itemId)
//
//                call.enqueue(object : Callback<ItemLikedResponseDTO> {
//                    override fun onResponse(
//                        call: Call<ItemLikedResponseDTO>,
//                        response: Response<ItemLikedResponseDTO>,
//                    ) {
//                        Log.d("isLiked", "찜하기 서버 통신 성공")
//                    }
//
//                    override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
//                        Log.d("isLiked", "찜하기 서버 통신 실패")
//                    }
//                })
            }
        }


        private fun updateLikeUI(isLiked : Boolean) {
            // UI 업데이트
            if (isLiked) {
                // 찜하기 상태: 하트가 빨간색으로 채워짐
                binding.likeBtn.setBackgroundResource(R.drawable.ic_heart_filled_20dp)
            } else {
                // 찜 취소 상태: 하트가 빈 상태
                binding.likeBtn.setBackgroundResource(R.drawable.ic_heart_unfilled_20dp)
            }
        }
    }
}
