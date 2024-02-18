package com.org.martall.view.store.user.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.org.martall.databinding.SortBottomSheetBinding
import com.org.martall.view.store.LocalStoreFragment

class SortBottomSheet : BottomSheetDialogFragment() {
    interface SortSelectionListener {
        fun onSortSelected(selectedSort: String)
    }

    private lateinit var binding : SortBottomSheetBinding

    // 콜백 리스너 변수
    private var sortSelectionListener: SortSelectionListener? = null

    // 콜백 설정 메서드
    fun setSortSelectionListener(listener: SortSelectionListener) {
        sortSelectionListener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SortBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.defaultSortTv.setOnClickListener {
            sortSelectionListener?.onSortSelected("기본 순")
            Log.d("BottomSheet", "BottomSheet - Selected Sort: 기본 순")
            dismiss() // 클릭 후 BottomSheet 닫음
        }

        binding.latestSortTv.setOnClickListener {
            sortSelectionListener?.onSortSelected("최신 순")
            Log.d("BottomSheet", "BottomSheet - Selected Sort: 최신 순")
            dismiss()
        }

        binding.membershipSortTv.setOnClickListener {
            sortSelectionListener?.onSortSelected("단골 지수 순")
            Log.d("BottomSheet", "BottomSheet - Selected Sort: 단골 지수 순")
            dismiss()
        }

        binding.heartSortTv.setOnClickListener {
            sortSelectionListener?.onSortSelected("찜 지수 순")
            Log.d("BottomSheet", "BottomSheet - Selected Sort: 찜 지수 순")
            dismiss()
        }
    }
}