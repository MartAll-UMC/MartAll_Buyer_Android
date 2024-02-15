package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.Model.DibsProductResponseDTO
import com.org.martall.databinding.ItemCategoryProductBinding
import java.text.NumberFormat
import java.util.Locale

class DibsProductRVAdapter (private val productList: List<DibsProductResponseDTO.DibsProducts>, private val dibsProductClickListener: DibsProductClickListener) : RecyclerView.Adapter<DibsProductRVAdapter.ViewHolder>() {
    interface DibsProductClickListener {
        fun onCancelDibsProduct(itemId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ) // 수정된 부분
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: DibsProductResponseDTO.DibsProducts) {
            val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(product.price)
            //binding.ivProductImg.setImageResource(product.picName)
            binding.tvProductName.text = product.itemName
            binding.tvProductPrice.text = "${formattedPrice}원"
            binding.tvMartName.text = product.martName
            // 찜 상태에 따라 버튼 이미지 변경
            if (product.like) {
                binding.btnLike.setImageResource(R.drawable.ic_like_unfilled_20dp)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
            }
            binding.btnLike.setOnClickListener {
                // 찜 상태 변경
                product.like = !product.like

                // 찜 상태에 따른 이벤트 처리
                if (product.like) {
                    binding.btnLike.setImageResource(R.drawable.ic_like_unfilled_20dp)
                } else {
                    binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
                    dibsProductClickListener.onCancelDibsProduct(product.itemId)
                }
            }

        }
    }
}