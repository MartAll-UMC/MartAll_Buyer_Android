package com.org.martall.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.databinding.ItemUserInfoBinding
import com.org.martall.model.UserDTO
import com.org.martall.model.dummyPosts

class UserAdapter : ListAdapter<UserDTO,UserAdapter.UserViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position])
    }



    inner class UserViewHolder(val binding: ItemUserInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: UserDTO) {
            with(binding) {
                tvNumberOfFollower.text="팔로워 수 ${item.followerCount}명"
                tvNumberOfVisitor.text="방문자 수 ${item.visitorCount}명"
                tvUserName.text=item.name
                tvTag.text=item.hashTag
                rvImage.apply {
                    layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    adapter=UserPostAdapter(dummyPosts)
                }
            }
        }
    }


    companion object {
      val diffUtil= object: DiffUtil.ItemCallback<UserDTO>() {
            override fun areItemsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
                return oldItem==newItem
            }
        }
    }
}

