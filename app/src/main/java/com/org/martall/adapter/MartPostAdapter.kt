package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemMartPostBinding
import com.org.martall.model.MartItemDTO
import com.org.martall.services.ApiServiceManager
import com.org.martall.services.ItemApiServiceManager
import java.text.NumberFormat

class MartPostAdapter(private val itemList: List<MartItemDTO>) :
    RecyclerView.Adapter<MartPostAdapter.ViewHolder>() {

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
                    toggleLikeState()
                }
            }


            fun bind(item: MartItemDTO) {
                binding.itemMartPostNameTv.text = item.name
                val formattedPrice = NumberFormat.getNumberInstance().format(item.price)
                binding.itemMartPostPriceTv.text = "${formattedPrice}원"
                Log.d("MartPostAdapter", "Image URL: ${item.imageUrl}")
                Glide.with(itemView.context).load(item.imageUrl).into(binding.localMartPropertyIv)

                // 초기 찜 UI
                binding.itemMartPostHeartIv.setImageResource(R.drawable.ic_heart_filled_20dp)
                // 클릭 시 찜 UI 변경
                updateLikeUI()
            }

        private fun toggleLikeState() {
            isLiked = !isLiked

            if (isLiked) {
                // 찜하기 서버 통신
                val apiService = ItemApiServiceManager.ItemapiService

                // 찜하기 성공 -> UI 업데이트
                updateLikeUI()
            } else {
                // 찜 취소 서버 통신

                // 찜 취소 성공 -> UI 업데이트
                updateLikeUI()
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