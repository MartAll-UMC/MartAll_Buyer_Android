package com.org.martall.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.martall.adapter.MartSimpleRVAdapter
import com.org.martall.adapter.SearchItemRVAdapter
import com.org.martall.databinding.FragmentSearchResultBinding
import com.org.martall.model.SearchResponse
import com.org.martall.model.dummyItems
import com.org.martall.services.ApiService
import retrofit2.Call
import retrofit2.Callback

class SearchResultFragment(private val isProduct: Boolean, private val keyword: String) :
    Fragment() {
    private lateinit var binding: FragmentSearchResultBinding
    private val apiMart = ApiService.createMartVer()
    private val apiItem = ApiService.createItemVer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        if (isProduct) {
            binding.searchResultRv.visibility = View.GONE
            binding.searchResultEmptyLl.visibility = View.GONE

            apiItem.searchItemList(keyword).enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: retrofit2.Response<SearchResponse>,
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        data?.items?.let { items ->
                            binding.searchResultTitleTv.text = "총 ${items.size}건"
                            binding.searchResultRv.adapter = SearchItemRVAdapter(items)

                            if (items.isEmpty()) {
                                binding.searchResultRv.visibility = View.GONE
                                binding.searchResultEmptyLl.visibility = View.VISIBLE
                            } else {
                                binding.searchResultRv.layoutManager = GridLayoutManager(context, 2)
                                binding.searchResultRv.visibility = View.VISIBLE
                                binding.searchResultEmptyLl.visibility = View.GONE
                            }
                            binding.loadingPb.visibility = View.GONE
                        }
                    } else {
                        Log.e("ERROR", "error")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e("ERROR", t.message ?: "complete failure")
                }
            })
        } else {
            binding.searchResultRv.visibility = View.GONE
            binding.searchResultEmptyLl.visibility = View.GONE

            apiMart.searchMartList(keyword).enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: retrofit2.Response<SearchResponse>,
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        data?.marts?.let { marts ->
                            binding.searchResultTitleTv.text = "총 ${marts.size}건"
                            binding.searchResultRv.adapter = MartSimpleRVAdapter(marts)

                            if (marts.isEmpty()) {
                                binding.searchResultRv.visibility = View.GONE
                                binding.searchResultEmptyLl.visibility = View.VISIBLE
                            } else {
                                binding.searchResultRv.layoutManager = LinearLayoutManager(context)
                                binding.searchResultRv.setPadding(0, 12, 0, 12)
                                binding.searchResultRv.visibility = View.VISIBLE
                                binding.searchResultEmptyLl.visibility = View.GONE
                            }
                            binding.loadingPb.visibility = View.GONE
                        }
                    } else {
                        Log.e("ERROR", "error")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e("ERROR", t.message ?: "error")
                }
            })
        }

        return binding.root
    }
}