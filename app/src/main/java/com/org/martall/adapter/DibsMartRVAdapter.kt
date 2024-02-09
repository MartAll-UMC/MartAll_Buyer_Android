package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.model.DibsMartResponseDTO
import com.org.martall.databinding.ItemDibsProductBinding // 수정된 부분

class DibsMartRVAdapter (private val MartList: List<DibsMartResponseDTO.DibsMarts>, private val onUnfollowClicked: (Int) -> Unit) : RecyclerView.Adapter<DibsMartRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDibsProductBinding.inflate(LayoutInflater.from(parent.context), parent, false) // 수정된 부분
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Mart = MartList[position]
        holder.bind(Mart)
    }

    override fun getItemCount(): Int {
        return MartList.size
    }
    inner class ViewHolder(val binding: ItemDibsProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Mart: DibsMartResponseDTO.DibsMarts) {
            //binding.ivMartImg.setImageResource(Mart.imageUrl)
            binding.martNameTv.text = Mart.martname
            binding.bookmarkBtn.setOnClickListener {
                Log.d("DibsMartRVAdapter", "Button clicked for mart ID: ${Mart.martshopId}")
                onUnfollowClicked(Mart.martshopId) }
        }
    }
}
