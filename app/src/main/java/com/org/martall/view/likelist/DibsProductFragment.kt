package com.org.martall.view.likelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.org.martall.adapter.DibsProductRVAdapter
import com.org.martall.models.DibsProductManager
import com.org.martall.databinding.FragmentDibsProductBinding
import com.org.martall.models.DibsProductResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DibsProductFragment : Fragment() {
    private lateinit var binding: FragmentDibsProductBinding
    private val productList: ArrayList<DibsProductResponseDTO.DibsProducts> = ArrayList()
    private var adapter: DibsProductRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDibsProductBinding.inflate(inflater, container, false)
        initializeRecyclerView()
        getLikedItems()
        return binding.root
    }

    private fun initializeRecyclerView() {
        adapter = DibsProductRVAdapter(productList) { itemId ->
            onCancelDibsProduct(itemId)
        }
        binding.rvProductList.adapter = adapter
    }

    private fun getLikedItems() {
        val apiService = DibsProductManager.dibsProductApiService
        val call = apiService.getDibsProduct()

        call.enqueue(object : Callback<DibsProductResponseDTO> {
            override fun onResponse(
                call: Call<DibsProductResponseDTO>, response: Response<DibsProductResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val dibsProducts = response.body()?.result?.item ?: emptyList()
                    updateRecyclerView(dibsProducts)
                } else {
                    // Handle error case
                }
            }

            override fun onFailure(call: Call<DibsProductResponseDTO>, t: Throwable) {
                Log.d("DibsProductFragment", "Failed to get liked items: ${t.message}")
                showToast("찜한 상품을 가져오는 데 실패했습니다.")
            }
        })
    }

    private fun updateRecyclerView(dibsProducts: List<DibsProductResponseDTO.DibsProducts>) {
        productList.clear()
        productList.addAll(dibsProducts)

        if (productList.isEmpty()) {
            binding.shopDibsLayout.root.visibility = View.VISIBLE
            binding.rvProductList.visibility = View.GONE
        } else {
            binding.shopDibsLayout.root.visibility = View.GONE
            binding.rvProductList.visibility = View.VISIBLE
            adapter?.notifyDataSetChanged()
        }
    }

    private fun onCancelDibsProduct(itemId: Int) {
        val apiService = DibsProductManager.dibsProductApiService
        apiService.cancelDibsProduct(itemId).enqueue(object : Callback<DibsProductResponseDTO> {
            override fun onResponse(
                call: Call<DibsProductResponseDTO>,
                response: Response<DibsProductResponseDTO>
            ) {
                if (response.isSuccessful) {
                    removeProductFromList(itemId)
                } else {
                    // Handle error case
                }
            }

            override fun onFailure(call: Call<DibsProductResponseDTO>, t: Throwable) {
                Log.d("DibsProductFragment", "Failed to cancel dibs: ${t.message}")
                showToast("찜 취소에 실패했습니다.")
            }
        })
    }

    private fun removeProductFromList(itemId: Int) {
        val position = productList.indexOfFirst { it.itemId == itemId }
        if (position != -1) {
            productList.removeAt(position)
            adapter?.notifyItemRemoved(position)
            adapter?.notifyItemRangeChanged(position, productList.size)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
