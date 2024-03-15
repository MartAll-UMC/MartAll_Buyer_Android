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
import com.org.martall.models.SearchItemResponse
import com.org.martall.models.SearchMartResponse
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class SearchResultFragment(private val isProduct: Boolean, private val keyword: String) :
    Fragment() {
    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var api: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            if (isProduct) {
                binding.searchResultRv.visibility = View.GONE
                binding.searchResultEmptyLl.visibility = View.GONE

                api.searchItemList(keyword).enqueue(object : Callback<SearchItemResponse> {
                    override fun onResponse(
                        call: Call<SearchItemResponse>,
                        response: retrofit2.Response<SearchItemResponse>,
                    ) {
                        val data = response.body()
                        Log.d("[PRINT/SEARCH]", "${response}")
                        if (data != null && (data.status == 200 || data.status == 4202)) {
                            data?.items?.let { items ->
                                binding.searchResultTitleTv.text = "총 ${items.size}건"
                                binding.searchResultRv.adapter = SearchItemRVAdapter(items)

                                if (items == null || items.isEmpty()) {
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
                            Log.e("[ERROR/SEARCH]", "상품 검색 API 에러")
                            binding.searchResultRv.visibility = View.GONE
                            binding.searchResultEmptyLl.visibility = View.VISIBLE
                            binding.loadingPb.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<SearchItemResponse>, t: Throwable) {
                        Log.e("[ERROR/SEARCH]", t.message ?: "API error")
                    }
                })
            } else {
                binding.searchResultRv.visibility = View.GONE
                binding.searchResultEmptyLl.visibility = View.GONE

                api.searchMartList(keyword).enqueue(object : Callback<SearchMartResponse> {
                    override fun onResponse(
                        call: Call<SearchMartResponse>,
                        response: retrofit2.Response<SearchMartResponse>,
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
                            Log.e("[ERROR/SEARCH]", "마트 검색 API 에러")
                        }
                    }

                    override fun onFailure(call: Call<SearchMartResponse>, t: Throwable) {
                        Log.e("[ERROR/SEARCH]", t.message ?: "Api error")
                    }
                })
            }
        }
        return binding.root
    }
}