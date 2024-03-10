package com.org.martall.view.home.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.CartItemAdapter
import com.org.martall.databinding.FragmentNotificationOrderBinding
import com.org.martall.models.NoticeOrderDTO
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationOrderFragment : Fragment() {
    lateinit var binding: FragmentNotificationOrderBinding // Fragment의 View Binding을 위한 변수 선언
    private lateinit var api: ApiService


    companion object {
        fun newInstance(): NotificationOrderFragment {
            return NotificationOrderFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotificationOrderBinding.inflate(inflater, container, false)
        getOrderStatus()
        return binding.root
    }

    // RecyclerView를 업데이트하는 메서드
    private fun updateRecyclerView(martResults: List<NoticeOrderDTO.MartResult.CartItem>) {
        if (martResults.isEmpty()) {
            binding.sumCoinLayout.visibility = View.GONE
            binding.rvOrderList.visibility = View.GONE
            binding.noticeOrderLayout.root.visibility = View.VISIBLE
            binding.rvStatusList.visibility = View.GONE
            Log.e("NetworkError", "no mart results")
        } else {
            binding.sumCoinLayout.visibility = View.VISIBLE
            binding.rvOrderList.visibility = View.VISIBLE
            binding.noticeOrderLayout.root.visibility = View.GONE
            binding.rvStatusList.visibility = View.VISIBLE

            binding.totalPriceTv.text = martResults.sumOf { it.price }.toString()
            binding.notificationMerchandiseTv3.text = martResults.size.toString()
            binding.rvStatusList.adapter = CartItemAdapter(martResults)
            Log.e("NetworkSuccess", "marts loaded: $martResults")
        }
    }

    // 서버로부터 주문 상태를 가져오는 메서드
    private fun getOrderStatus() {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getOrderStatus().enqueue(object : Callback<NoticeOrderDTO> {
                override fun onResponse(
                    call: Call<NoticeOrderDTO>,
                    response: Response<NoticeOrderDTO>,
                ) {
                    if (response.isSuccessful) {
                        val martResults = response.body()?.result
                        updateRecyclerView(martResults!!.cartItems)
                    } else {
                        Log.e("NetworkError", "Response Error: " + response.errorBody()?.string())
                    }
                }

                override fun onFailure(call: Call<NoticeOrderDTO>, t: Throwable) {
                    Log.e("NetworkError", "Failed to fetch data: " + t.message)
                }
            })
        }

    }
}
