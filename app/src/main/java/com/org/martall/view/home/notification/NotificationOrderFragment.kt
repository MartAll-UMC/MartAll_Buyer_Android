package com.org.martall.view.home.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.Model.NoticeOrderDTO
import com.org.martall.Model.NoticeOrderManager
import com.org.martall.databinding.FragmentNotificationOrderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationOrderFragment : Fragment() {
    lateinit var binding: FragmentNotificationOrderBinding // Fragment의 View Binding을 위한 변수 선언


    companion object {
        fun newInstance(): NotificationOrderFragment {
            return NotificationOrderFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationOrderBinding.inflate(inflater, container, false)
        getOrderStatus()
        return binding.root
    }

    // RecyclerView를 업데이트하는 메서드
    private fun updateRecyclerView(martResults: List<NoticeOrderDTO.MartResult>) {
        if (martResults.isEmpty()) {
            binding.noticeOrderLayout.root.visibility = View.VISIBLE
            binding.rvOrderList.visibility = View.GONE
            Log.e("NetworkError", "no mart results")
        } else {
            binding.noticeOrderLayout.root.visibility = View.GONE
            binding.rvOrderList.visibility = View.VISIBLE
            Log.e("NetworkSuccess", "marts loaded: $martResults")
        }
    }

    // 서버로부터 주문 상태를 가져오는 메서드
    private fun getOrderStatus() {
        val apiService = NoticeOrderManager.NoticeOrderApiService
        val call = apiService.getOrderStatus()

        call.enqueue(object : Callback<NoticeOrderDTO> {
            override fun onResponse(
                call: Call<NoticeOrderDTO>,
                response: Response<NoticeOrderDTO>
            ) {
                if (response.isSuccessful) {
                    //val martResults = response.body()?.result ?: emptyList()
                    //updateRecyclerView(martResults)
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
