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
import com.org.martall.databinding.FragmentCategoryFishBinding
import com.org.martall.interfaces.CategoryService
import com.org.martall.interfaces.MartItemdibs
import com.org.martall.models.SecondItem
import com.org.martall.models.SecondResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryFishFragment : Fragment() {

    private lateinit var binding: FragmentCategoryFishBinding

    companion object {
        fun newInstance(): CategoryFishFragment {
            return CategoryFishFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryFishBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.MOCK_CART_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val categoryService = retrofit.create(CategoryService::class.java)
        val categoryCall = categoryService.getCategoryItem("수산", 0, 1000, "기본")
        val martItemdibs = retrofit.create(MartItemdibs::class.java)

        Log.d("Retrofit", "Making server request...")

        categoryCall.enqueue(object : Callback<SecondResponse> {
            override fun onResponse(
                call: Call<SecondResponse>,
                response: Response<SecondResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("Retrofit", "Server request successful")
                    val categoryResponse = response.body()
                    categoryResponse?.result?.let { categoryResult ->
                        val categoryItems = categoryResult.item // Modify here
                        val adapter = CategoryRVAdapter(categoryItems, martItemdibs)
                        binding.rvProductList.adapter = adapter
                        binding.rvProductList.layoutManager = GridLayoutManager(requireContext(), 2)
                    }
                } else {
                    Log.e("Retrofit", "Server request failed with code ${response.code()}")
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<SecondResponse>, t: Throwable) {
                Log.e("Retrofit", "Server request failed", t)
                // Handle failure
            }
        })

        return binding.root
    }
}
