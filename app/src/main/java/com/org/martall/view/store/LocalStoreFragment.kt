package com.org.martall.view.store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.martall.adapter.MartRVAdapter
import com.org.martall.databinding.FragmentLocalStoreBinding
import com.org.martall.model.MartDataDTO
import com.org.martall.model.MartListResponseDTO
import com.org.martall.model.dummyData
import com.org.martall.services.ApiServiceManager
import com.org.martall.view.store.user.bottomsheet.FilterBottomSheet
import com.org.martall.view.store.user.bottomsheet.SortBottomSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.fragment.app.activityViewModels
import com.org.martall.ViewModel.SharedMartViewModel

class LocalStoreFragment : Fragment() {
    private lateinit var binding: FragmentLocalStoreBinding
    private val sharedMartViewModel: SharedMartViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLocalStoreBinding.inflate(inflater, container, false)

        // initToolBar()
        initRecyclerView()

        binding.filterTv.setOnClickListener {
            showFilterBottomSheet()
        }

        binding.sortTv.setOnClickListener {
            showSortBottomSheet()
        }

//        val martRVAdapter = MartRVAdapter(dummyData)
//        binding.groupRecyclerView.adapter = martRVAdapter



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMartData()
    }

    private fun initRecyclerView() {
        with(binding) {
            groupRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun showFilterBottomSheet() {
        FilterBottomSheet().show(
            childFragmentManager,
            null
        )
    }

    private fun showSortBottomSheet() {
        SortBottomSheet().show(
            childFragmentManager,
            null
        )
    }

    private fun loadMartData() {
        val apiService = ApiServiceManager.MartapiService
        val call = apiService.getAllShops()

        call.enqueue(object : Callback<MartListResponseDTO> {
            override fun onResponse(
                call: Call<MartListResponseDTO>,
                response: Response<MartListResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val martList = response.body()?.marts ?: emptyList()
                    // 데이터 설정
                    sharedMartViewModel.setMartList(martList)

                    updateRecyclerView(martList)
                } else {
                    // Handle server error
                }
            }

            override fun onFailure(call: Call<MartListResponseDTO>, t: Throwable) {
                Log.d("check", "마트 전체 조회 연결 실패")
            }
        })
    }

    private fun updateRecyclerView(martList: List<MartDataDTO>) {
        val martRVAdapter = MartRVAdapter(martList) { selectedMart ->
            // 사용자가 마트를 선택했을 때, 해당 마트의 정보를 SharedViewModel에 설정
            // sharedMartViewModel.setSelectedMart(selectedMart)

            val intent = Intent(requireContext(), MartDetailInfoActivity::class.java)
            intent.putExtra("martId", selectedMart.martId)
            startActivity(intent)
        }
        binding.groupRecyclerView.adapter = martRVAdapter
//        Log.d("MartRVAdapter", "Adapter set with click listener")
    }
}
