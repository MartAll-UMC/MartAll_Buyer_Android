package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.models.ItemSearchDTO

class SearchItemRVAdapter(private var itemList: List<ItemSearchDTO>) :
    RecyclerView.Adapter<SearchItemRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): SearchItemRVAdapter.ViewHolder {
        val binding: ItemCategoryProductBinding = ItemCategoryProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchItemRVAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val idx = adapterPosition
            }
        }

        fun bind(item: ItemSearchDTO) {
            Glide.with(itemView).load(item.img).into(binding.ivProductImg)
            binding.tvProductName.text = item.name
            binding.tvProductPrice.text = "${item.price}원"
            binding.tvMartName.text = item.store
            if (item.isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }

            binding.btnLike.setOnClickListener {
                item.isLiked = !item.isLiked
                bind(item)
            }
        }
    }
}