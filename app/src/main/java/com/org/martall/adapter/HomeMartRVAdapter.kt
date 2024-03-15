package com.org.martall.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.databinding.ItemRecommendMartBinding
import com.org.martall.models.RecommendedMart
import com.org.martall.view.home.NewMerchActivity
import com.org.martall.view.store.ProductDetailActivity

class HomeMartRVAdapter(private var martList: List<RecommendedMart>) :
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
        val currentMart = martList[position]
        holder.bind(currentMart)
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

        fun bind(mart: RecommendedMart) {
            binding.apply {
                martLogoTv.text = mart.name
                recommendMartTv.text = mart.name
                recommendMartTagTv.text = mart.martcategory.joinToString(" ") { "#$it" }
                Glide.with(itemView.context)
                    .load(mart.photo)
                    .into(recommendMartIv)

            }
        }
    }
}
