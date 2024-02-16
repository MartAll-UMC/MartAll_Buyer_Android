package com.org.martall.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.model.DibsMartUnfollowDTO
import com.org.martall.R
import com.org.martall.model.DibsMartResponseDTO
import com.org.martall.databinding.ItemDibsProductBinding // 수정된 부분

class DibsMartRVAdapter (private val MartList: List<DibsMartResponseDTO.DibsMarts>, private val onUnfollowClicked: (Int) -> Unit) : RecyclerView.Adapter<DibsMartRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDibsProductBinding.inflate(LayoutInflater.from(parent.context), parent, false) // 수정된 부분
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Mart = MartList[position]
        holder.bind(Mart)
    }

    override fun getItemCount(): Int {
        return MartList.size
    }
    inner class ViewHolder(val binding: ItemDibsProductBinding) : RecyclerView.ViewHolder(binding.root) {
        // ViewHolder 클래스 내부
        fun bind(Mart: DibsMartResponseDTO.DibsMarts) {
            binding.martNameTv.text = Mart.martname
            var isFollowed = false // 임시적으로 팔로우 상태를 관리하는 변수

            updateButtonAppearance(isFollowed)

            binding.bookmarkBtn.setOnClickListener {
                // 팔로우 상태 토글
                isFollowed = !isFollowed
                updateButtonAppearance(isFollowed)

                // 팔로우 상태에 따라 적절한 액션 수행
                if (!isFollowed) {
                    onUnfollowClicked(Mart.martshopId)
                }
            }
        }

        private fun updateButtonAppearance(isFollowed: Boolean) {
            if (isFollowed) {
                binding.bookmarkBtn.setBackgroundResource(R.drawable.bg_blue_border20)
                binding.bookmarkBtn.text = "단골 추가"
                binding.bookmarkBtn.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))

            } else {
                binding.bookmarkBtn.setBackgroundResource(R.drawable.btn_blue_line20)
                binding.bookmarkBtn.text = "단골 가게"
                binding.bookmarkBtn.setTextColor(ContextCompat.getColor(binding.root.context, R.color.primary_color))
            }
        }

        // onBindViewHolder 내부
        fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val Mart = MartList[position]
            holder.bind(Mart)
        }

    }

}
