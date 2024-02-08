package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.model.DibsProductResponseDTO
import com.org.martall.databinding.ItemCategoryProductBinding // 수정된 부분

class DibsProductRVAdapter (private val productList: List<DibsProductResponseDTO.DibsProducts>) : RecyclerView.Adapter<DibsProductRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false) // 수정된 부분
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    inner class ViewHolder(val binding: ItemCategoryProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: DibsProductResponseDTO.DibsProducts) {
            //binding.ivProductImg.setImageResource(product.imageUrl)
            binding.tvProductName.text = product.itemName
            binding.tvProductPrice.text = "${product.price}원"
            //binding.tvMartName.text = product.
            /*if (product.isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }

            binding.btnLike.setOnClickListener {
                product.isLiked = !product.isLiked
                bind(product)
            }*/
        }
    }
}
