package com.org.martall.view.likelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.Model.DibsMartManager
import com.org.martall.adapter.DibsMartRVAdapter
import com.org.martall.databinding.FragmentDibsMartBinding
import com.org.martall.models.DibsMartResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DibsMartFragment : Fragment() {
    lateinit var binding: FragmentDibsMartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDibsMartBinding.inflate(inflater, container, false)
        getLikedMarts()
        return binding.root
    }

    private fun getLikedMarts(){
        val apiService = DibsMartManager.dibsMartApiService
        val call = apiService.getDibsMart()

        call.enqueue(object : Callback<DibsMartResponseDTO>{
            override fun onResponse(
                call: Call<DibsMartResponseDTO>,
                response: Response<DibsMartResponseDTO>
            ) {
                if (response.isSuccessful){
                    val dibsMarts = response.body()?.followedMarts ?: emptyList()
                    updateRecyclerView(dibsMarts)
                } else {
                    // 에러 처리
                }
            }

            override fun onFailure(call: Call<DibsMartResponseDTO>, t: Throwable) {
                Log.d("check", "failed")
            }
        })
    }

    private fun updateRecyclerView(dibsMarts: List<DibsMartResponseDTO.DibsMarts>){
        if (dibsMarts.isEmpty()){
            binding.shopDibsLayout.root.visibility = View.VISIBLE
            binding.groupRecyclerView.visibility = View.GONE
        } else {
            binding.shopDibsLayout.root.visibility = View.GONE
            binding.groupRecyclerView.visibility = View.VISIBLE
            binding.groupRecyclerView.adapter = DibsMartRVAdapter(dibsMarts) { martShopId ->
                unfollowMart(martShopId)
            }
        }
    }

    private fun unfollowMart(martShopId: Int) {
        Log.d("DibsMartFragment", "Unfollowing mart with ID: $martShopId")
        val apiService = DibsMartManager.dibsMartApiService
        apiService.cancelDibsMart(martShopId).enqueue(object : Callback<DibsMartResponseDTO> {
            override fun onResponse(
                call: Call<DibsMartResponseDTO>,
                response: Response<DibsMartResponseDTO>
            ) {
                if (response.isSuccessful) {
                    getLikedMarts() // 목록 새로고침
                } else {
                    // 에러 처리
                }
            }

            override fun onFailure(call: Call<DibsMartResponseDTO>, t: Throwable) {
                // 실패 처리
            }
        })
    }
}
