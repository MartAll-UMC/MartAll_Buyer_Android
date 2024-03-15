package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.databinding.ItemOrderStatusBinding
import com.org.martall.models.NoticeOrderDTO
import java.text.NumberFormat
import java.util.Locale

class CartItemAdapter(private val cartItemList: List<NoticeOrderDTO.MartResult.CartItem>) :
    RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = ItemOrderStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(cartItemList[position])
    }

    override fun getItemCount(): Int = cartItemList.size

    inner class CartItemViewHolder(private val binding: ItemOrderStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: NoticeOrderDTO.MartResult.CartItem) {
            val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(cartItem.price)

            binding.notificationOrderCategoryTv.text = cartItem.categoryName
            binding.notificationOrderNameTv.text = cartItem.itemName
            binding.notificationOrderPriceTv.text = "${formattedPrice}원"
            Glide.with(itemView.context).load(cartItem.picName).into(binding.notificationOrderIv)

        }
    }
}