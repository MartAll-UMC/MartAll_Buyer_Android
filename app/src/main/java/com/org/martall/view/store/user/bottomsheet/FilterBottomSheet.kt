package com.org.martall.view.store.user.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider
import com.org.martall.R
import com.org.martall.databinding.FragmentFilterBottomSheetBinding
import kotlinx.coroutines.selects.select

class FilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentFilterBottomSheetBinding

    private var selectedChipText: String? = null
    var previousSelectedChip: Chip? = null

    private var selectedMembershipCountMin: Int? = null
    private var selectedMembershipCountMax: Int? = null
    private var selectedHeartCountMin: Int? = null
    private var selectedHeartCountMax: Int? = null

    interface OnFilterAppliedListener {
        fun onFilterApplied(
            selectedChipText: String?,
            selectedMembershipCountMin: Int?,
            selectedMembershipCountMax: Int?,
            selectedHeartCountMin: Int?,
            selectedHeartCountMax: Int?
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater,container,false)

        binding.sliderMembershipCount.stepSize = 1F
        binding.sliderHeartCount.stepSize = 1F

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.cgFilterItem.setOnCheckedChangeListener { group, checkedId ->
//            val selectedChip = group.findViewById<Chip>(checkedId)
//            selectedChipText = selectedChip?.text?.toString()
//            Log.d("filterBottomSheet", selectedChipText ?: "No chip selected")
//        }

        binding.chipChildItem.setOnClickListener {
            selectedChipText = binding.chipChildItem.text.toString()
//            updateChipTextColor(binding.chipChildItem)
        }

        binding.chipCosmetics.setOnClickListener {
            selectedChipText = binding.chipCosmetics.text.toString()
//            updateChipTextColor(binding.chipCosmetics)
        }

        binding.chipFood.setOnClickListener {
            selectedChipText = binding.chipFood.text.toString()
//            updateChipTextColor(binding.chipFood)

        }

        binding.chipFish.setOnClickListener {
            selectedChipText = binding.chipFish.text.toString()
//            updateChipTextColor(binding.chipFish)
        }

        binding.chipHealth.setOnClickListener {
            selectedChipText = binding.chipHealth.text.toString()
//            updateChipTextColor(binding.chipHealth)
        }

        binding.chipAnimal.setOnClickListener {
            selectedChipText = binding.chipAnimal.text.toString()
//            updateChipTextColor(binding.chipAnimal)
        }

        binding.chipMeat.setOnClickListener {
            selectedChipText = binding.chipMeat.text.toString()
//            updateChipTextColor(binding.chipMeat)
        }

        val membershipCountSlider = binding.sliderMembershipCount
        membershipCountSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                Log.d("FilterBottomSheet", "단골 - 터치 시작")
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // 터치 종료 시 처리
                selectedMembershipCountMin = slider.values[0].toInt()
                selectedMembershipCountMax = slider.values[1].toInt()
            }
        })

        val heartCountSlider = binding.sliderHeartCount
        heartCountSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                Log.d("FilterBottomSheet", "찜 - 터치 시작")
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // 터치 종료 시 처리
                selectedHeartCountMin = slider.values[0].toInt()
                selectedHeartCountMax = slider.values[1].toInt()
            }
        })

        val applyButton = binding.bottomsheetSaveBtn
        applyButton.setOnClickListener {
            val listener = targetFragment as? OnFilterAppliedListener
            listener?.onFilterApplied(
                selectedChipText,
                selectedMembershipCountMin,
                selectedMembershipCountMax,
                selectedHeartCountMin,
                selectedHeartCountMax
            )
            dismiss()
        }
    }

    // Chip 텍스트 색상 업데이트 함수
    @SuppressLint("ResourceType")
    private fun updateChipTextColor(chip: Chip) {
        // 이전에 선택된 Chip이 있다면 색상 초기화
        previousSelectedChip?.setTextAppearanceResource(R.color.grey400)
        Log.d("updateChipTextColor", "색상 업데이트 확인")
        // 선택된 Chip의 텍스트 색상 변경
        chip.setTextAppearanceResource(R.style.MyChipStyle)

        // 이전에 선택된 Chip을 현재 선택된 Chip으로 업데이트
        previousSelectedChip = chip
    }
}