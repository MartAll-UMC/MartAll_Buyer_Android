package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemMartDetailPostBinding
import com.org.martall.model.MartDataDTO
import com.org.martall.model.MartItemDTO
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.NumberFormat

class MartDetailRVAdapter(private val martProduct: MartDataDTO) :
    RecyclerView.Adapter<MartDetailRVAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(martId: Int, itemId: Int)
    }

    // 리스너 변수
    private var onItemClickListener: OnItemClickListener? = null

    // 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MartDetailRVAdapter.ViewHolder {
        val binding = ItemMartDetailPostBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartDetailRVAdapter.ViewHolder, position: Int) {
        holder.bind(martProduct.items[position])
    }

    override fun getItemCount(): Int {
        return martProduct.items.size
    }

    inner class ViewHolder(val binding: ItemMartDetailPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isLiked: Boolean = false

        init {
            binding.root.setOnClickListener {
                val martId = martProduct.martId
                val itemId = martProduct.items[adapterPosition].itemId

                // 리스너가 설정되어 있다면 실행
                onItemClickListener?.onItemClick(martId, itemId)
            }

            binding.itemMartPostHeartIv.setOnClickListener {
                // 클릭되었을 때의 동작을 정의
                val isChecked = !binding.itemMartPostHeartIv.isSelected
                // isChecked 값에 따라 서버와 통신하여 토글 처리
                handleToggle(isChecked)
                // 선택 상태를 업데이트
                binding.itemMartPostHeartIv.isSelected = isChecked
            }
        }

        fun bind(item: MartItemDTO) {
            binding.martNameTv.text = martProduct.name
            binding.itemMartPostNameTv.text = item.name

            val formattedPrice = NumberFormat.getNumberInstance().format(item.price)
            binding.itemMartPostPriceTv.text = "${formattedPrice}원"
            Log.d("MartPostAdapter", "Image URL: ${item.imageUrl}")
            Glide.with(itemView.context).load(item.imageUrl).into(binding.localMartPropertyIv)

            // 초기 찜 UI
            // binding.itemMartPostHeartIv.setImageResource(R.drawable.ic_heart_filled_20dp)
            // 초기 토글 상태 설정
            binding.itemMartPostHeartIv.isSelected = isLiked
            // 클릭 시 찜 UI 변경
            // updateLikeUI()
        }

        private fun handleToggle(isChecked: Boolean) {
            // isChecked 값에 따라 서버와 통신하여 토글 처리
            if (isChecked) {
                // 선택됨: 찜하기
                // 서버 통신 및 UI 업데이트
                updateLikeUI(true)
            } else {
                // 선택 해제됨: 찜 취소
                // 서버 통신 및 UI 업데이트
                updateLikeUI(false)
            }
        }

        private fun updateLikeUI(isLiked: Boolean) {
            // UI 업데이트
            if (isLiked) {
                // 찜하기 상태: 하트가 빨간색으로 채워짐
                binding.itemMartPostHeartIv.setBackgroundResource(R.drawable.ic_heart_filled_20dp)
            } else {
                // 찜 취소 상태: 하트가 빈 상태
                binding.itemMartPostHeartIv.setBackgroundResource(R.drawable.ic_heart_unfilled_20dp)
            }
        }
    }
}
