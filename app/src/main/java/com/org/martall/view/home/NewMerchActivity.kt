package com.org.martall.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.org.martall.BuildConfig
import com.org.martall.adapter.SimpleProductRVAdapter
import com.org.martall.databinding.ActivityNewMerchBinding
import com.org.martall.interfaces.MartItemService
import com.org.martall.interfaces.MartItemdibs
import com.org.martall.model.Item
import com.org.martall.model.Response
import com.org.martall.model.SecondItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewMerchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewMerchBinding
    private lateinit var martItemdibs: MartItemdibs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMerchBinding.inflate(layoutInflater)
        binding.backIb.setOnClickListener {
            finish()
        }
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("${BuildConfig.MOCK_CART_URL}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 데이터 로딩 전 스켈레톤 뷰 표시
        showNewMerchData(isLoading = true)

        // Retrofit 인터페이스 생성
        val service = retrofit.create(MartItemService::class.java)
        val call = service.getNewItem()
        martItemdibs = retrofit.create(MartItemdibs::class.java)

        // Retrofit 호출 실행
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val newItemResponse = response.body()
                    newItemResponse?.result?.let { items ->
                        // 리싸이클러뷰 어댑터 생성 및 설정
                        val adapter = SimpleProductRVAdapter(items, martItemdibs)
                        binding.productListRv.adapter = adapter
                        binding.productListRv.layoutManager = GridLayoutManager(this@NewMerchActivity, 2, GridLayoutManager.VERTICAL, false)
                    }
                    // Retrofit 호출 성공 로그
                    Log.d("Retrofit", "Retrofit call successful")
                } else {
                    // Retrofit 호출 실패 로그
                    Log.e("Retrofit", "Retrofit call failed with code: ${response.code()}")
                }
                showNewMerchData(isLoading = false)
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                // Retrofit 호출 실패 로그
                Log.e("Retrofit", "Retrofit call failed", t)
                showNewMerchData(isLoading = false)
            }
        })
    }

    private fun showNewMerchData(isLoading: Boolean) {
        if (isLoading) {
            binding.newMerchShimmerFl.startShimmer()
            binding.newMerchShimmerFl.visibility = View.VISIBLE
            binding.productListRv.visibility = View.GONE
        } else {
            binding.newMerchShimmerFl.stopShimmer()
            binding.newMerchShimmerFl.visibility = View.GONE
            binding.productListRv.visibility = View.VISIBLE
        }
    }
}
