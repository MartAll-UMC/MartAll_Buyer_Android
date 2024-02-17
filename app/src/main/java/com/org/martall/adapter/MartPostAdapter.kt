package com.org.martall.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemMartPostBinding
import com.org.martall.model.MartItemDTO
import com.org.martall.model.MartLikedResponseDTO
import com.org.martall.services.ApiServiceManager
import com.org.martall.services.ItemApiServiceManager
import com.org.martall.view.store.ProductDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class MartPostAdapter(private val itemList: List<MartItemDTO>, private val martId: Int) :
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

                    val intent = Intent(binding.root.context, ProductDetailActivity::class.java)
                    intent.putExtra(ProductDetailActivity.EXTRA_ITEM_ID, martId)
                    intent.putExtra(ProductDetailActivity.EXTRA_MART_ID, itemId)
                    Log.d("MartPostAdapter", "martId: " + martId.toString() + "itemId: " + itemId.toString())
                    binding.root.context.startActivity(intent)
                }

                binding.itemMartPostHeartIv.setOnClickListener {
                    toggleLikeState()
                }
            }


            fun bind(item: MartItemDTO) {
                binding.itemMartPostNameTv.text = item.name
                val formattedPrice = NumberFormat.getNumberInstance().format(item.price)
                binding.itemMartPostPriceTv.text = "${formattedPrice}원"
                Log.d("MartPostAdapter", "Image URL: ${item.imageUrl}")
                Glide.with(itemView.context).load(item.imageUrl).into(binding.localMartPropertyIv)

                isLiked = item.likeYn
                if (isLiked)
                    binding.itemMartPostHeartIv.setImageResource(R.drawable.ic_heart_filled_20dp)
                else
                    binding.itemMartPostHeartIv.setImageResource(R.drawable.ic_heart_unfilled_20dp)
            }

        private fun toggleLikeState() {

            if (isLiked) {

                // 찜취소 -> UI 업데이트
                isLiked = !isLiked
                updateLikeUI()

                // 찜취소 서버 통신
                val apiService = ItemApiServiceManager.ItemapiService
                val call = apiService.unLikedItem(itemId = itemList[adapterPosition].itemId)

                call.enqueue(object : Callback<MartLikedResponseDTO> {
                    override fun onResponse(
                        call: Call<MartLikedResponseDTO>,
                        response: Response<MartLikedResponseDTO>
                    ) {
                        Log.d("isLiked", "찜 취소 서버 통신 성공")
                    }

                    override fun onFailure(call: Call<MartLikedResponseDTO>, t: Throwable) {
                        Log.d("isLiked", "찜 취소 서버 통신 실패")
                    }
                })
            } else {
                isLiked = !isLiked
                // 찜하기 성공 -> UI 업데이트
                updateLikeUI()

                // 찜하기 서버 통신
                val apiService = ItemApiServiceManager.ItemapiService
                val call = apiService.likedItem(itemId = itemList[adapterPosition].itemId)

                call.enqueue(object : Callback<MartLikedResponseDTO>{
                    override fun onResponse(
                        call: Call<MartLikedResponseDTO>,
                        response: Response<MartLikedResponseDTO>
                    ) {
                        Log.d("isLiked", "찜하기 서버 통신 성공")
                    }

                    override fun onFailure(call: Call<MartLikedResponseDTO>, t: Throwable) {
                        Log.d("isLiked", "찜하기 서버 통신 실패")
                    }

                })
            }
        }

        private fun updateLikeUI() {
            // isLiked에 따라 UI 업데이트
            if (isLiked) {
                // 찜하기 상태: 하트가 빨간색으로 채워짐
                binding.itemMartPostHeartIv.setImageResource(R.drawable.ic_heart_filled_20dp)
            } else {
                // 찜 취소 상태: 하트가 빈 상태
                binding.itemMartPostHeartIv.setImageResource(R.drawable.ic_heart_unfilled_20dp)
            }
        }

        }
    }