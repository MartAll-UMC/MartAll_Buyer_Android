package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.model.DibsProductResponseDTO
import com.org.martall.databinding.ItemCategoryProductBinding // 수정된 부분

class DibsProductRVAdapter (private val productList: List<DibsProductResponseDTO.DibsProducts>, private val dibsProductClickListener: DibsProductClickListener) : RecyclerView.Adapter<DibsProductRVAdapter.ViewHolder>() {
    interface DibsProductClickListener {
        fun onCancelDibsProduct(itemId: Int)
    }

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
            binding.btnLike.setOnClickListener {
                // 찜 상태가 변경되었을 때 이벤트 처리
                if (product.isLiked) {
                    // 이미 찜한 상태라면 서버에 찜 취소 요청
                    dibsProductClickListener.onCancelDibsProduct(product.itemId)
                }
                product.isLiked = !product.isLiked
                bind(product)
            }
        }
    }
}
