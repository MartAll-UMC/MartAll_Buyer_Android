package com.org.martall.view.likelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.org.martall.adapter.BookMarkRVAdapter
import com.org.martall.databinding.FragmentBookmarkBinding
import com.org.martall.models.BookMarkManager
import com.org.martall.models.BookMarkResponseDTO
import com.org.martall.models.FollowResponseDTO
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookMarkFragment : Fragment() {
    lateinit var binding: FragmentBookmarkBinding
    private lateinit var api: ApiService
    private val martList: ArrayList<BookMarkResponseDTO.DibsMarts> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        getLikedMarts() //단골마트 불러오기
        return binding.root
    }

    private fun getLikedMarts() {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getDibsMart().enqueue(object : Callback<BookMarkResponseDTO> {
                override fun onResponse(
                    call: Call<BookMarkResponseDTO>,
                    response: Response<BookMarkResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.result ?: emptyList()
                        Log.d("BookMarkFragment", "API call successful: ${response.body()}")
                        Log.d("BookMarkFragment", "result size: ${result.size}")
                        Log.d("BookMarkFragment", "Result: $result")

                        martList.clear() // 현재 목록을 초기화하고 새 데이터로 업데이트
                        martList.addAll(result)
                        updateRecyclerView(martList) // RecyclerView를 업데이트
                    } else {
                        Log.d("BookMarkFragment", "API Response not successful, code: ${response.code()}, message: ${response.message()}")
                        showToast("단골 마트 목록을 가져오는 데 실패했습니다.")
                    }
                }

                override fun onFailure(call: Call<BookMarkResponseDTO>, t: Throwable) {
                    Log.d("BookMarkFragment", "API call failed: ${t.message}")
                    showToast("단골 마트 목록을 가져오는 데 실패했습니다.")
                }
            })
        }
    }

    private fun updateRecyclerView(dibsMarts: List<BookMarkResponseDTO.DibsMarts>) {
        if (dibsMarts.isEmpty()) {
            // 단골마트 없을 경우
            binding.bookmarkLayout.root.visibility = View.VISIBLE
            binding.rvBookmarkList.visibility = View.GONE
        } else {
            // 단골마트 있을 경우
            binding.bookmarkLayout.root.visibility = View.GONE
            binding.rvBookmarkList.visibility = View.VISIBLE
            binding.rvBookmarkList.adapter = BookMarkRVAdapter(dibsMarts) { martShopId ->
                unfollowMart(martShopId) // 관심 목록에서 제거하는 함수를 호출
            }
        }
    }


    private fun unfollowMart(martId: Int) {
        Log.d("DibsMartFragment", "Unfollowing mart with ID: $martId")
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())
            api.unfollowMart(martId).enqueue(object : Callback<FollowResponseDTO> {
                override fun onResponse(
                    call: Call<FollowResponseDTO>,
                    response: Response<FollowResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        removeMartFromList(martId) // 목록에서 제거
                        Log.d("DibsMartFragment", "Unfollow: $martId succeeded")
                    } else {
                        Log.d("DibsMartFragment", "Unfollow: $martId failed")
                    }
                }

                // 요청 실패 시
                override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                    Log.d("DibsMartFragment", "API call failed: ${t.message}")
                }
            })

        }
//        Log.d("DibsMartFragment", "Unfollowing mart with ID: $martId")
//        val apiService = BookMarkManager.dibsMartApiService
//        apiService.unfollowMart(martId).enqueue(object : Callback<FollowResponseDTO> {
//            override fun onResponse(
//                call: Call<FollowResponseDTO>,
//                response: Response<FollowResponseDTO>,
//            ) {
//                if (response.isSuccessful) {
//                    removeMartFromList(martId) // 목록에서 제거
//                    Log.d("DibsMartFragment", "Unfollow: $martId succeeded")
//                } else {
//                    Log.d("DibsMartFragment", "Unfollow: $martId failed")
//                }
//            }
//
//            // 요청 실패 시
//            override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
//                Log.d("DibsMartFragment", "API call failed: ${t.message}")
//            }
//        })
    }


    private fun removeMartFromList(martId: Int) {
        val position = martList.indexOfFirst { it.martId == martId }
        if (position != -1) {
            martList.removeAt(position)
            binding.rvBookmarkList.adapter?.notifyItemRemoved(position) //아이템 제거
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
