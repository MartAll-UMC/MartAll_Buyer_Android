package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.Model.DibsMartResponseDTO
import com.org.martall.databinding.ItemDibsProductBinding

class DibsMartRVAdapter(
    private val MartList: ArrayList<DibsMartResponseDTO.DibsMarts>,
    private val onUnfollowClicked: (Int) -> Unit // 팔로우 해제 이벤트를 처리
) : RecyclerView.Adapter<DibsMartRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDibsProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun bind(Mart: DibsMartResponseDTO.DibsMarts) {

            binding.martNameTv.text = Mart.martname
            binding.followerCountTv.text = Mart.visitorCount.toString()
            binding.dibsCountTv.text = Mart.salesIndex.toString()

            // 초기 버튼 상태 업데이트 (팔로우/언팔로우 상태에 따라)
            updateButtonAppearance(Mart.isfollowed)

            // 북마크(팔로우/언팔로우) 버튼 클릭 이벤트 처리
            binding.bookmarkBtn.setOnClickListener {
                Mart.isfollowed = !Mart.isfollowed // 팔로우 상태 토글
                updateButtonAppearance(Mart.isfollowed) // 버튼 상태 업데이트

                // 언팔로우 상태일 때 콜백 함수 호출
                if (!Mart.isfollowed) {
                    onUnfollowClicked(Mart.martshopId)
                }
            }
        }

        // 팔/언팔 상태에 따라 버튼 업데이트
        private fun updateButtonAppearance(isFollowed: Boolean) {
            if (isFollowed) {
                // 팔로우 상태일 때
                binding.bookmarkBtn.setBackgroundResource(R.drawable.btn_blue_line20)
                binding.bookmarkBtn.text = "단골 가게"
                binding.bookmarkBtn.setTextColor(ContextCompat.getColor(binding.root.context, R.color.primary_color))
            } else {
                // 언팔로우 상태일 때
                binding.bookmarkBtn.setBackgroundResource(R.drawable.bg_blue_border20)
                binding.bookmarkBtn.text = "단골 추가"
                binding.bookmarkBtn.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            }
        }
    }
}