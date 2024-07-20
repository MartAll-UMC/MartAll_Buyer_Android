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
    private var likeStates: MutableList<Boolean> = martItem.items.map { it.likeYn }.toMutableList()

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
        holder.bind(martItem.items[position], likeStates[position])
    }

    override fun getItemCount(): Int {
        return martItem.items.size
    }

    inner class ViewHolder(val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val martId = martItem.martId
                val itemId = martItem.items[adapterPosition].itemId
                onItemClickListener?.onItemClick(martId, itemId)
            }

            // 찜 기능
            binding.likeBtn.setOnClickListener {

                Log.d("[LIKE]", likeStates.toString())

                val position = adapterPosition
                likeStates[position] = !likeStates[position]
                updateLikeUI(likeStates[position])

                if(likeStates[position]) {
                    api.likedItem(martItem.martId)
                        .enqueue(object : Callback<ItemLikedResponseDTO> {
                            override fun onResponse(
                                call: Call<ItemLikedResponseDTO>,
                                response: Response<ItemLikedResponseDTO>,
                            ) {
                                if (response.isSuccessful) {
                                    // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                                    Log.d("[LIKE]", "Update request successful for item $likeStates[position]")
                                } else {
                                    Log.e("[LIKE]", "Failed to send update request for item $likeStates[position]")
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                likeStates[position] = !likeStates[position]
                                updateLikeUI(likeStates[position])
                                Log.e(
                                    "[LIKE]",
                                    "Failed to send update request for item $itemId + $likeStates[position]"
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
                                    Log.d("[LIKE]", "Update request successful for item $likeStates[position]")
                                } else {
                                    Log.d("[LIKE]", "Update request successful for item $likeStates[position]")
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                likeStates[position] = !likeStates[position]
                                updateLikeUI(likeStates[position])
                            }
                        })
                }
                }
        }

        fun bind(item: MartItemDTO, isLiked: Boolean) {
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

            updateLikeUI(item.likeYn)
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
