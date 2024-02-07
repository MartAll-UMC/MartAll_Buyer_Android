package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemRecommendKeywordBinding
import com.org.martall.view.search.SearchActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecommendKeywordRVAdapter(
    private var keywordList: List<String>,
    private val isSecond: Boolean = false,
) :
    RecyclerView.Adapter<RecommendKeywordRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): RecommendKeywordRVAdapter.ViewHolder {
        val binding: ItemRecommendKeywordBinding = ItemRecommendKeywordBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendKeywordRVAdapter.ViewHolder, position: Int) {
        holder.bind(keywordList[position])
    }

    override fun getItemCount(): Int {
        return keywordList.size
    }

    inner class ViewHolder(val binding: ItemRecommendKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.keywordTv.setOnClickListener {
                Log.d("PRINT", "keyword: ${binding.keywordTv.text}")
                (binding.root.context as SearchActivity).binding.searchBar.searchBarEt.setText(
                    binding.keywordTv.text
                )
                GlobalScope.launch {
                    (binding.root.context as SearchActivity).search()
                }
            }
        }

        fun bind(keyword: String) {
            val rank = if (isSecond) adapterPosition + 6 else adapterPosition + 1
            binding.rankTv.text = rank.toString()
            binding.keywordTv.text = keyword
            if (rank < 4) {
                binding.rankTv.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.primary400
                    )
                )
            }
        }
    }
}