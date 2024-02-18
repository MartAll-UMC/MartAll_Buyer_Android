package com.org.martall.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.Model.NoticeOrderDTO
import com.org.martall.databinding.FragmentNotificationOrderBinding

class MartAdapter(private val martList: List<NoticeOrderDTO.MartResult>) :
    RecyclerView.Adapter<MartAdapter.MartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MartViewHolder {
        val binding = FragmentNotificationOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartViewHolder, position: Int) {
        holder.bind(martList[position])
    }

    override fun getItemCount(): Int = martList.size

    inner class MartViewHolder(private val binding: FragmentNotificationOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(martResult: NoticeOrderDTO.MartResult) {
            binding.notificationMerchandiseTv1.text = martResult.mart.martName
            binding.rvStatusList.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
            binding.rvStatusList.adapter = CartItemAdapter(martResult.cartItems)
        }
    }
}