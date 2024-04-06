package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.models.LikeItemResponseDTO
import java.text.NumberFormat
import java.util.Locale

class LikeItemRVAdapter(
    private val productList: List<LikeItemResponseDTO.DibsProducts>,
    private val onCancelDibsProduct: (Int) -> Unit,
) : RecyclerView.Adapter<LikeItemRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
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
        fun bind(product: LikeItemResponseDTO.DibsProducts) {

            val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(product.price)
            Glide.with(itemView).load(product.picName).into(binding.itemImgIv)
            binding.itemNameTv.text = product.itemName
            binding.itemPriceTv.text = "${formattedPrice}원"
            binding.martNameTv.text = product.martName

            setLikeButtonImage(product.like)

            // 찜 버튼 클릭 이벤트.
            binding.likeBtn.setOnClickListener {
                product.like = !product.like
                setLikeButtonImage(product.like)

                if (!product.like) {
                    onCancelDibsProduct(product.itemId)
                }
            }
        }

        // 찜 상태에 따른 버튼 이미지 설정
        private fun setLikeButtonImage(isLiked: Boolean) {
            if (isLiked) { //찜한 상태
                binding.likeBtn.setImageResource(R.drawable.ic_like_filled_20dp)
            } else { //찜취소
                binding.likeBtn.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }
        }
    }
}

