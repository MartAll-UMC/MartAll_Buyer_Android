package com.org.martall.view.category

import CategoryRVAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.org.martall.BuildConfig
import com.org.martall.databinding.FragmentCategoryInnerBinding
import com.org.martall.interfaces.MartItemdibs
import com.org.martall.models.SecondResponse
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryInnerFragment(private val tabName: String) : Fragment() {

    private lateinit var binding: FragmentCategoryInnerBinding
    private lateinit var api: ApiService

    companion object {
        fun newInstance(tabName: String): CategoryInnerFragment {
            return CategoryInnerFragment(tabName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoryInnerBinding.inflate(inflater, container, false)

        GlobalScope.launch {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.MOCK_CART_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val martItemdibs = retrofit.create(MartItemdibs::class.java)

            api = ApiService.createWithHeader(requireContext())

            api.getCategoryItem(tabName, 0, 100000, "기본")
                .enqueue(object : Callback<SecondResponse> {
                    override fun onResponse(
                        call: Call<SecondResponse>,
                        response: Response<SecondResponse>,
                    ) {
                        if (response.isSuccessful) {
                            Log.d("Retrofit", "Server request successful")
                            val categoryResponse = response.body()
                            categoryResponse?.result?.let { categoryItems ->
                                val adapter =
                                    CategoryRVAdapter(categoryItems, martItemdibs)
                                binding.rvProductList.adapter = adapter
                                binding.rvProductList.layoutManager =
                                    GridLayoutManager(requireContext(), 2)
                            }
                        } else {
                            Log.e("Retrofit", "Server request failed with code ${response.code()}")
                            // Handle error response
                        }
                    }

                    override fun onFailure(call: Call<SecondResponse>, t: Throwable) {
                        Log.e("Retrofit", "Server request failed", t)
                    }
                })
        }

        return binding.root
    }
}
