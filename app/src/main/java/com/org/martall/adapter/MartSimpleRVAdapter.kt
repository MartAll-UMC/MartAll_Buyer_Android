package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemDibsProductBinding
import com.org.martall.models.MartSimpleDTO

class MartSimpleRVAdapter(private var martList: List<MartSimpleDTO>) :
    RecyclerView.Adapter<MartSimpleRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): MartSimpleRVAdapter.ViewHolder {
        val binding: ItemDibsProductBinding = ItemDibsProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartSimpleRVAdapter.ViewHolder, position: Int) {
        holder.bind(martList[position])
    }

    override fun getItemCount(): Int {
        return martList.size
    }

    inner class ViewHolder(val binding: ItemDibsProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val idx = adapterPosition
            }
        }

        fun bind(mart: MartSimpleDTO) {
            binding.martNameTv.text = mart.name
            binding.martProfileIv.text = mart.name
            var hashTag = ""
            for (category in mart.categories) {
                hashTag += "#${category} "
            }
            binding.martHashtagTv2.text = ""
            binding.martHashtagTv3.text = ""
            binding.martHashtagTv1.text = hashTag
            binding.followerCountTv.text = mart.followerCnt.toString()
            binding.dibsCountTv.text = mart.likeCnt.toString()
            if (mart.isFollowed) {
                binding.bookmarkBtn.text = "단골 가게"
                binding.bookmarkBtn.isSelected = true
                binding.bookmarkBtn.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.primary400
                    )
                )
            } else {
                binding.bookmarkBtn.text = "단골 추가"
                binding.bookmarkBtn.isSelected = false
            }
        }
    }
}