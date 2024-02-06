package com.org.martall.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.RecentKeywordRVAdapter
import com.org.martall.adapter.RecommendKeywordRVAdapter
import com.org.martall.databinding.FragmentSearchBeforeBinding
import com.org.martall.model.dummyKeywordItems
import com.org.martall.utils.ListToDataStoreUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchBeforeFragment(private val isProduct: Boolean) :
    Fragment() {
    lateinit var binding: FragmentSearchBeforeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBeforeBinding.inflate(inflater, container, false)
        var keywords : MutableList<String> = mutableListOf()

        GlobalScope.launch(Dispatchers.Main) {
            val dataStore = requireContext().dataStore
            keywords = ListToDataStoreUtil().getList(dataStore, "recentKeywords").toMutableList()

            if (keywords.isEmpty()) {
                binding.recentSearchTitleTv.visibility = View.GONE
                binding.recentSearchRv.visibility = View.GONE
            } else {
                binding.recentSearchTitleTv.visibility = View.VISIBLE
                binding.recentSearchRv.visibility = View.VISIBLE
                binding.recentSearchRv.adapter = RecentKeywordRVAdapter(keywords)
            }
        }

        binding.recommendKeywords1Rv.adapter = RecommendKeywordRVAdapter(
            dummyKeywordItems.subList(0, 5)
        )
        binding.recommendKeywords2Rv.adapter = RecommendKeywordRVAdapter(
            dummyKeywordItems.subList(5, 10),
            true
        )

        return binding.root
    }
}