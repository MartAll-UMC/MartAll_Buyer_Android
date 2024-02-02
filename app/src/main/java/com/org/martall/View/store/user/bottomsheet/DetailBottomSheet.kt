package com.org.martall.view.store.user.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.org.martall.databinding.MartDetailBottomsheetBinding
import com.org.martall.databinding.SortBottomSheetBinding

class DetailBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : MartDetailBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MartDetailBottomsheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 이벤트 처리
    }

}