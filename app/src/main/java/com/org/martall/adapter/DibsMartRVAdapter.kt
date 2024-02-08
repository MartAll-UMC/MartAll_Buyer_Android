package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.model.DibsMartResponseDTO
import com.org.martall.databinding.ItemDibsProductBinding // 수정된 부분

class DibsMartRVAdapter (private val MartList: List<DibsMartResponseDTO.DibsMarts>) : RecyclerView.Adapter<DibsMartRVAdapter.ViewHolder>() {

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
            //binding.tvMartPrice.text = "${Mart.price}원"
            //binding.tvMartName.text = Mart.
            /*if (Mart.isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }

            binding.btnLike.setOnClickListener {
                Mart.isLiked = !Mart.isLiked
                bind(Mart)
            }*/
        }
    }
}
