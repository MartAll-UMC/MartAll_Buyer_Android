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
import com.org.martall.model.dummyData
import com.org.martall.view.search.SearchActivity
import com.org.martall.view.store.user.bottomsheet.FilterBottomSheet
import com.org.martall.view.store.user.bottomsheet.SortBottomSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.fragment.app.activityViewModels
import com.org.martall.ViewModel.SharedMartViewModel
import com.org.martall.model.MartDataDTO
import com.org.martall.model.MartListResponseDTO
import com.org.martall.services.ApiServiceManager
import com.org.martall.services.CartApiServiceManager
import kotlinx.coroutines.selects.select

class LocalStoreFragment : Fragment(), SortBottomSheet.SortSelectionListener,
    FilterBottomSheet.OnFilterAppliedListener {
    private lateinit var binding: FragmentLocalStoreBinding
    private val sharedMartViewModel: SharedMartViewModel by activityViewModels()
    private lateinit var martRVAdapter: MartRVAdapter

    private var tag: String? = null
    private var minBookmark: Int? = null
    private var maxBookmark: Int? = null
    private var minLiked: Int? = null
    private var maxLiked: Int? = null
    private var sort: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLocalStoreBinding.inflate(inflater, container, false)

        initRecyclerView()

        binding.tbShop.ivSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("isProductSearch", false)
            startActivity(intent)
        }

//        val martRVAdapter = MartRVAdapter(dummyData)
//        binding.groupRecyclerView.adapter = martRVAdapter

        binding.sortTv.setOnClickListener {
            showSortBottomSheet()
        }

        binding.filterTv.setOnClickListener {
            showFilterBottomSheet()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 초기화할 때 빈 리스트로 어댑터 생성
        martRVAdapter = MartRVAdapter(emptyList()) { selectedMart ->
            // 사용자가 마트를 선택했을 때, 해당 마트의 정보를 처리
            val intent = Intent(requireContext(), MartDetailInfoActivity::class.java)
            intent.putExtra("martId", selectedMart.martId)
            startActivity(intent)
        }

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
        val filterBottomSheet = FilterBottomSheet()
        filterBottomSheet.setTargetFragment(this, 0)  // 이 부분이 중요합니다.
        filterBottomSheet.show(parentFragmentManager, filterBottomSheet.tag)
    }

    private fun showSortBottomSheet() {
        val bottomSheet = SortBottomSheet()

        // 콜백 설정
        bottomSheet.setSortSelectionListener(this)
        bottomSheet.show(childFragmentManager, null)
    }

    // 콜백 메서드 override
    override fun onSortSelected(selectedSort: String) {
        Log.d("BottomSheet", "LocalStore - Selected Sort: $selectedSort")
        binding.sortTv.text = selectedSort
        sort = selectedSort
        updateFilterUI()
    }

    override fun onFilterApplied(
        selectedChipText: String?,
        selectedMembershipCountMin: Int?,
        selectedMembershipCountMax: Int?,
        selectedHeartCountMin: Int?,
        selectedHeartCountMax: Int?
    ) {
        Log.d(
            "LocalStoreFragment", "Filter Applied: " +
                    "tag : $selectedChipText" +
                    "Membership Count Min: $selectedMembershipCountMin, " +
                    "Membership Count Max: $selectedMembershipCountMax, " +
                    "Heart Count Min: $selectedHeartCountMin, " +
                    "Heart Count Max: $selectedHeartCountMax"
        )

        tag = selectedChipText
        minBookmark = selectedMembershipCountMin
        maxBookmark = selectedMembershipCountMax
        minLiked = selectedHeartCountMin
        maxLiked = selectedHeartCountMax

        updateFilterUI()
    }

    private fun updateFilterUI() {
        val apiService = CartApiServiceManager.CartapiService
        val call = apiService.ShowAllShops(tag = tag, minBookmark = minBookmark, maxBookmark = maxBookmark,
            minLike = minLiked, maxLike = maxLiked, sort = sort)

        call.enqueue(object : Callback<MartListResponseDTO> {
            override fun onResponse(
                call: Call<MartListResponseDTO>,
                response: Response<MartListResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val martList = response.body()?.result ?: emptyList()
                    Log.d("updateFilterUI", "응답 성공")
                    // 기존 어댑터에 데이터 설정 후 갱신
                    Log.d("updateFilterUI", "어댑터 갱신 전") // 추가
                    (binding.groupRecyclerView.adapter as? MartRVAdapter)?.setData(martList)
                    (binding.groupRecyclerView.adapter as? MartRVAdapter)?.notifyDataSetChanged()
                    Log.d("updateFilterUI", "어댑터 갱신 후") // 추가
                } else {
                    // Handle server error
                }
            }

            override fun onFailure(call: Call<MartListResponseDTO>, t: Throwable) {
                Log.d("check", "마트 전체 조회 연결 실패")
            }
        })
    }

    // 초기 UI 구성 서버 통신
    private fun loadMartData() {
        val apiService = CartApiServiceManager.CartapiService
        val call = apiService.ShowAllShops(tag = null, minBookmark = null, maxBookmark = null,
            minLike = null, maxLike = null, sort = null)

        call.enqueue(object : Callback<MartListResponseDTO> {
            override fun onResponse(
                call: Call<MartListResponseDTO>,
                response: Response<MartListResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val martList = response.body()?.result ?: emptyList()
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
        martRVAdapter.setData(martList)
        binding.groupRecyclerView.adapter = martRVAdapter
    }
}
