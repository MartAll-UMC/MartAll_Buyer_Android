package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.databinding.ItemRecommendMartBinding
import com.org.martall.models.RecommendedMart
import kotlin.random.Random

class HomeMartRVAdapter(martList: List<RecommendedMart>) :
    RecyclerView.Adapter<HomeMartRVAdapter.ViewHolder>() {

    private var todayMartList: List<RecommendedMart> = martList.shuffled().take(5)

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
        val currentMart = todayMartList[position]
        holder.bind(currentMart)
    }

    override fun getItemCount(): Int {
        return todayMartList.size
    }

    inner class ViewHolder(val binding: ItemRecommendMartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val idx = adapterPosition
                // 클릭 이벤트 처리 로직 추가 가능
            }
        }

        fun bind(mart: RecommendedMart) {
            binding.apply {
                martLogoTv.text = mart.martName
                recommendMartTv.text = mart.martName
                recommendMartTagTv.text = mart.martCategory.joinToString(" ") { "#$it" }
                Glide.with(itemView.context)
                    .load(mart.martImg)
                    .into(recommendMartIv)
            }
        }
    }
}