package com.org.martall.view.likelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.DibsProductRVAdapter
import com.org.martall.Model.DibsProductManager
import com.org.martall.databinding.FragmentDibsProductBinding
import com.org.martall.Model.DibsProductResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DibsProductFragment : Fragment() {
    private lateinit var binding: FragmentDibsProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDibsProductBinding.inflate(inflater, container, false)
        getLikedItems()
        return binding.root
    }

    private fun getLikedItems() {
        val apiService = DibsProductManager.dibsProductApiService
        val call = apiService.getDibsProduct()

        call.enqueue(object : Callback<DibsProductResponseDTO> {
            override fun onResponse(
                call: Call<DibsProductResponseDTO>, response: Response<DibsProductResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val dibsProducts = response.body()?.result?.item?:emptyList()
                    updateRecyclerView(dibsProducts)
                } else {
                    // Handle the error case
                }
            }

            override fun onFailure(call: Call<DibsProductResponseDTO>, t: Throwable) {
                Log.d("DibsProductFragment", "Failed to get liked items: ${t.message}")
            }
        })
    }

    private fun updateRecyclerView(dibsProducts: List<DibsProductResponseDTO.DibsProducts>) {
        if (dibsProducts.isEmpty()) {
            binding.shopDibsLayout.root.visibility = View.VISIBLE
            binding.rvProductList.visibility = View.GONE
        } else {
            binding.shopDibsLayout.root.visibility = View.GONE
            binding.rvProductList.visibility = View.VISIBLE
            binding.rvProductList.adapter = DibsProductRVAdapter(dibsProducts, object : DibsProductRVAdapter.DibsProductClickListener {
                override fun onCancelDibsProduct(itemId: Int) {
                    // 찜 취소 API 호출
                    val apiService = DibsProductManager.dibsProductApiService
                    apiService.cancelDibsProduct(itemId).enqueue(object : Callback<DibsProductResponseDTO> {
                        override fun onResponse(call: Call<DibsProductResponseDTO>, response: Response<DibsProductResponseDTO>) {
                            if (response.isSuccessful) {
                                // 성공적으로 찜 취소. 상태 업데이트 필요
                                getLikedItems() // 상품 목록을 다시 가져와서 UI를 업데이트
                            }
                        }

                        override fun onFailure(call: Call<DibsProductResponseDTO>, t: Throwable) {
                            // 요청 실패 처리
                            Log.d("DibsProductFragment", "Failed to cancel dibs: ${t.message}")
                        }
                    })
                }
            })
        }
    }
}

