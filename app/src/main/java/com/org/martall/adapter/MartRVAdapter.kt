package com.org.martall.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.databinding.ItemMartListBinding
import com.org.martall.model.MartDTO
import com.org.martall.view.store.MartDetailInfoActivity

class MartRVAdapter(private val martList: List<MartDTO>) :
    RecyclerView.Adapter<MartRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    // 아이템 클릭 리스너
   private interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MartRVAdapter.ViewHolder {
        val binding: ItemMartListBinding =
            ItemMartListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartRVAdapter.ViewHolder, position: Int) {
        holder.bind(martList[position])
//        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(it, position)
//        }
    }

    override fun getItemCount(): Int {
        return martList.size
    }

    inner class ViewHolder(val binding: ItemMartListBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    val position = adapterPosition
                    startActivity(binding.root.context, Intent(binding.root.context, MartDetailInfoActivity::class.java), null)
                }
            }
        fun bind(mart: MartDTO) {
            binding.martNameTv.text = mart.name
            binding.martProfileIv.text = mart.name
            binding.martHashtagTv1.text = mart.hashTag
            binding.followerCountTv.text = mart.followerCount.toString()
            binding.dibsCountTv.text = mart.visitorCount.toString()
            binding.martImageRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = UserPostAdapter(mart.post) //UserPostAdapter(dummyPosts)
            }
        }
    }

}