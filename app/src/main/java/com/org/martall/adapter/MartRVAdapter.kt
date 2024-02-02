package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.databinding.ItemMartListBinding
import com.org.martall.model.UserDTO
import com.org.martall.model.dummyPosts

class MartRVAdapter(private val MartList : List<UserDTO>) : RecyclerView.Adapter<MartRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener : OnItemClickListener

//    interface MyItemClickListener {
//        fun onItemClick()
//    }

//    private lateinit var mItemClickListener : MyItemClickListener
//    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
//        mItemClickListener = itemClickListener
//    }


    // 아이템 클릭리스너
    interface OnItemClickListener {
        fun onClick(view: View, position: Int) {
            // Toast.makeText(view.context, "테스트 - ${position}클릭", Toast.LENGTH_SHORT).show()
        }
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MartRVAdapter.ViewHolder {
        val binding: ItemMartListBinding = ItemMartListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartRVAdapter.ViewHolder, position: Int) {
        holder.bind(MartList[position])
//        holder.itemView.setOnClickListener {
//            mItemClickListener.onItemClick()
//        }
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return MartList.size
    }

    inner class ViewHolder(val binding: ItemMartListBinding):  RecyclerView.ViewHolder(binding.root) {

        fun bind(mart: UserDTO) {
            binding.martNameTv.text = mart.name
            binding.martHashtagTv1.text = mart.hashTag
            binding.followerCountTv.text= "단골 수 ${mart.followerCount}"
            binding.visiterCountTv.text= "상품 찜 수 ${mart.visitorCount}"
            binding.martImageRecyclerView.apply {
                layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                adapter=UserPostAdapter(dummyPosts)
            }
        }
    }

}