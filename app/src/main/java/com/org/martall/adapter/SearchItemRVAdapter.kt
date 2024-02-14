package com.org.martall.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.model.ItemDTO
import com.org.martall.model.ItemSearchDTO

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
//            binding.ivProductImg.setImageResource(Uri.parse(item.img))
            binding.tvProductName.text = item.name
            binding.tvProductPrice.text = "${item.price}원"
            binding.tvMartName.text = item.store
            if (item.isLiked == "Y") {
                binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }

            binding.btnLike.setOnClickListener {
                if (item.isLiked == "Y") {
                    item.isLiked = "N"
                } else {
                    item.isLiked = "Y"
                }
                bind(item)
            }
        }
    }
}