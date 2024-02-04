package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.databinding.ItemRecommendMartBinding
import com.org.martall.model.MartDTO

class HomeMartRVAdapter(private var martList: List<MartDTO>) :
    RecyclerView.Adapter<HomeMartRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): HomeMartRVAdapter.ViewHolder {
        val binding: ItemRecommendMartBinding = ItemRecommendMartBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeMartRVAdapter.ViewHolder, position: Int) {
        holder.bind(martList[position])
    }

    override fun getItemCount(): Int {
        return martList.size
    }

    inner class ViewHolder(val binding: ItemRecommendMartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val idx = adapterPosition
            }
        }

        fun bind(mart: MartDTO) {
            binding.martLogoTv.text = mart.name
            binding.recommendMartTv.text = mart.name
            binding.recommendMartTagTv.text = mart.hashTag
            binding.recommendMartIv.visibility = View.INVISIBLE
        }
    }
}