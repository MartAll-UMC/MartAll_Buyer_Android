package com.org.martall.view.mart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.martall.adapter.MartRVAdapter
import com.org.martall.databinding.FragmentMartBinding
import com.org.martall.models.MartDataDTO
import com.org.martall.models.MartListResponseDTO
import com.org.martall.services.ApiService
import com.org.martall.view.cart.CartActivity
import com.org.martall.view.mart.user.bottomsheet.FilterBottomSheet
import com.org.martall.view.mart.user.bottomsheet.SortBottomSheet
import com.org.martall.view.search.SearchActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartFragment : Fragment(),
    SortBottomSheet.SortSelectionListener,
    FilterBottomSheet.OnFilterAppliedListener {

    private lateinit var binding: FragmentMartBinding

    // private val sharedMartViewModel: SharedMartViewModel by activityViewModels()
    private lateinit var api: ApiService

    // 필터
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
        binding = FragmentMartBinding.inflate(inflater, container, false)

        initRecyclerView()

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.ShowAllShops(tag, minBookmark, maxBookmark, minLiked, maxLiked, sort)
                .enqueue(object : Callback<MartListResponseDTO> {
                    override fun onResponse(
                        call: Call<MartListResponseDTO>,
                        response: Response<MartListResponseDTO>,
                    ) {
                        if (response.isSuccessful) {
                            val martResponse = response.body()
                            martResponse?.result?.let { items ->
                                Log.d("[MART/FRAGMENT]", "MartFragment items: $items")
                                val adapter = MartRVAdapter(items, { selectedMart ->
                                    val intent =
                                        Intent(requireContext(), MartDetailInfoActivity::class.java)
                                    intent.putExtra("martId", selectedMart.martId)
                                    startActivity(intent)
                                }, api)

                                binding.martListRecyclerView.adapter = adapter
                                binding.martListRecyclerView.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            }
                        } else {
                            Log.d("[MART/PRINT]", "MartList failed")
                        }
                    }

                    override fun onFailure(call: Call<MartListResponseDTO>, t: Throwable) {
                        Log.d("check", "마트 전체 조회 연결 실패")
                    }
                })
        }

        binding.tbMart.ivSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("isProductSearch", false)
            startActivity(intent)
        }

        binding.tbMart.ivCart.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }

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
    }

    private fun initRecyclerView() {
        with(binding) {
            martListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
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

        if (selectedSort === "기본" || selectedSort === "최신") {
            binding.sortTv.text = selectedSort + "순"
        } else {
            binding.sortTv.text = selectedSort + " 지수 순"
        }

        sort = selectedSort
        updateFilterUI()
    }

    private fun showFilterBottomSheet() {
        val filterBottomSheet = FilterBottomSheet()
        filterBottomSheet.setTargetFragment(this, 0)
        filterBottomSheet.show(parentFragmentManager, filterBottomSheet.tag)
    }

    override fun onFilterApplied(
        selectedChipText: String?,
        selectedMembershipCountMin: Int?,
        selectedMembershipCountMax: Int?,
        selectedHeartCountMin: Int?,
        selectedHeartCountMax: Int?,
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

    /*
    private fun showSortBottomSheet() {
        SortBottomSheet().show(
            childFragmentManager,
            null
        )
    }
     */

    private fun updateRecyclerView(martList: List<MartDataDTO>) {
        val martRVAdapter = MartRVAdapter(martList, { selectedMart ->
            val intent = Intent(requireContext(), MartDetailInfoActivity::class.java)
            intent.putExtra("martId", selectedMart.martId)
            startActivity(intent)
        }, api)
        binding.martListRecyclerView.adapter = martRVAdapter
    }


    private fun updateFilterUI() {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.ShowAllShops(tag, minBookmark, maxBookmark, minLiked, maxLiked, sort)
                .enqueue(object : Callback<MartListResponseDTO> {
                    override fun onResponse(
                        call: Call<MartListResponseDTO>,
                        response: Response<MartListResponseDTO>,
                    ) {
                        if (response.isSuccessful) {
                            val martList = response.body()?.result ?: emptyList()
                            Log.d("updateFilterUI", "응답 성공")
                            // 기존 어댑터에 데이터 설정 후 갱신
                            Log.d("updateFilterUI", "어댑터 갱신 전") // 추가
                            (binding.martListRecyclerView.adapter as? MartRVAdapter)?.setData(
                                martList
                            )
                            (binding.martListRecyclerView.adapter as? MartRVAdapter)?.notifyDataSetChanged()
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
    }
}
