package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.databinding.ItemPostImageBinding
import com.org.martall.model.Post

class UserPostAdapter(val posts: List<Post>) : RecyclerView.Adapter<UserPostAdapter.PostViewHolder>() {


    inner class PostViewHolder(val binding: ItemPostImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Post) {
                with(binding) {
                    ivPostImage.setOnClickListener {
                        // TODO 다음 화면으로 넘기기
                    }
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

}