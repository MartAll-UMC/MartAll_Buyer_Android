package com.org.martall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.databinding.ItemRecentKeywordBinding
import com.org.martall.utils.ListToDataStoreUtil
import com.org.martall.view.search.SearchActivity
import com.org.martall.view.search.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecentKeywordRVAdapter(
    private var keywords: MutableList<String>,
) :
    RecyclerView.Adapter<RecentKeywordRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): RecentKeywordRVAdapter.ViewHolder {
        val binding: ItemRecentKeywordBinding = ItemRecentKeywordBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentKeywordRVAdapter.ViewHolder, position: Int) {
        holder.bind(keywords[position])
    }

    override fun getItemCount(): Int {
        return keywords.size
    }

    inner class ViewHolder(val binding: ItemRecentKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recentKeywordTv.setOnClickListener {
                (binding.root.context as SearchActivity).binding.searchBar.searchBarEt.setText(
                    binding.recentKeywordTv.text
                )
                GlobalScope.launch {
                    (binding.root.context as SearchActivity).search()
                }
            }

            binding.recentKeywordRemoveIb.setOnClickListener {
                keywords.remove(binding.recentKeywordTv.text.toString())
                notifyItemRemoved(adapterPosition)
                GlobalScope.launch(Dispatchers.Main) {
                    removeKeyword(binding.recentKeywordTv.text.toString())
                }
            }
        }

        fun bind(keyword: String) {
            binding.recentKeywordTv.text = keyword
        }

        private suspend fun removeKeyword(keyword: String) {
            val dataStore = binding.root.context.dataStore
            val listToDataStoreUtil = ListToDataStoreUtil()

            var newKeywords: MutableList<String> =
                (listToDataStoreUtil.getList(dataStore, "recentKeywords")).toMutableList()
            newKeywords.remove(keyword)
            listToDataStoreUtil.saveList(dataStore, "recentKeywords", newKeywords.toList())
        }
    }
}