package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemMartPostBinding
import com.org.martall.models.ItemSimpleDTO

class UserPostAdapter(private val items: List<ItemSimpleDTO>) :
    RecyclerView.Adapter<UserPostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemMartPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemSimpleDTO) {
            binding.localMartPropertyIv.setImageResource(item.imageUrl)
            with(binding) {
                itemMartPostNameTv.text = item.name
                itemMartPostPriceTv.text = item.price.toString()
                var res = if (item.isLiked) {
                    R.drawable.ic_like_filled_20dp
                } else {
                    R.drawable.ic_like_unfilled_20dp
                }
                itemMartPostHeartIv.setImageResource(res)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemMartPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

}