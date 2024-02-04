package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.databinding.ItemDibsProductBinding
import com.org.martall.model.MartDTO

class MartSimpleRVAdapter(private var martList: List<MartDTO>) :
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

        fun bind(mart: MartDTO) {
            binding.martNameTv.text = mart.name
            binding.martProfileIv.text = mart.name
            binding.martHashtagTv1.text = mart.hashTag
            binding.followerCountTv.text = mart.followerCount.toString()
            binding.dibsCountTv.text = mart.visitorCount.toString()
        }
    }
}