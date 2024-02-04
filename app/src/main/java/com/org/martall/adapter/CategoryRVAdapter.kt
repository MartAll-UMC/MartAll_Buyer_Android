package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.model.ItemDTO

class CategoryRVAdapter(private val itemList: List<ItemDTO>) :
    RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): CategoryRVAdapter.ViewHolder {
        val binding: ItemCategoryProductBinding = ItemCategoryProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemDTO) {
            binding.ivProductImg.setImageResource(item.imageUrl)
            binding.tvProductName.text = item.name
            binding.tvProductPrice.text = "${item.price}원"
            binding.tvMartName.text = item.store
            if (item.isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }
        }
    }
}