package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemHomeProductBinding
import com.org.martall.model.ItemDTO

class ProductSimpleRVAdapter(private var itemList: List<ItemDTO>) :
    RecyclerView.Adapter<ProductSimpleRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): ProductSimpleRVAdapter.ViewHolder {
        val binding: ItemHomeProductBinding = ItemHomeProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductSimpleRVAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemHomeProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val idx = adapterPosition
            }
        }

        fun bind(item: ItemDTO) {
            binding.productImgIv.setImageResource(item.imageUrl)
            binding.productNameTv.text = item.name
            binding.productPriceTv.text = "${item.price}원"
            if (item.isLiked) {
                binding.productLikeIb.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.productLikeIb.setImageResource(R.drawable.ic_heart_line_20dp)
            }
            binding.productLikeIb.setOnClickListener {
                item.isLiked = !item.isLiked
                bind(item)
            }
        }
    }
}