package com.org.martall.view.store.user.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.org.martall.databinding.MartDetailBottomsheetBinding
import com.org.martall.model.MartDataDTO
import com.org.martall.model.MartDetailDTO
import com.org.martall.model.MartDetailResponseDTO
import com.org.martall.model.MartListResponseDTO
import com.org.martall.services.ApiServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : MartDetailBottomsheetBinding

    companion object {
        fun newInstance(martId: Int): DetailBottomSheet {
            return DetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt("martId", martId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MartDetailBottomsheetBinding.inflate(inflater,container,false)

        // 인텐트에서 martId 받아오기
        val martId: Int = arguments?.getInt("martId") ?: -1
        Log.d("DetailmartId", martId.toString())

        // TODO: martId를 사용하여 서버에서 데이터를 가져와 UI에 표시
        loadMartDetail(martId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadMartDetail(martId: Int) {
        val apiService = ApiServiceManager.MartapiService
        val call = apiService.getMartDetail(martId)

        call.enqueue(object : Callback<MartDetailResponseDTO> {
            override fun onResponse(
                call: Call<MartDetailResponseDTO>,
                response: Response<MartDetailResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val martDetailResponse = response.body()
                    Log.d("DetailBottomSheet", "응답 성공: $martDetailResponse")

                    val martDetail = martDetailResponse?.data
                    Log.d("DetailBottomSheet", "MartDetailDTO: $martDetail")

                    if (martDetail != null) {
                        updateUI(martDetail)
                    } else {
                        Log.e("DetailBottomSheet", "응답 성공하지만 데이터가 null입니다.")
                    }
                } else {
                    Log.e("DetailBottomSheet", "응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MartDetailResponseDTO>, t: Throwable) {
                Log.e("DetailBottomSheet", "서버 통신 실패", t)
            }
        })
    }

    private fun updateUI(martDetail: MartDetailDTO) {
        binding.martNameTv.text = martDetail.name
        binding.martNumberTv.text = martDetail.shopnumber
        binding.martTimeTv.text = martDetail.operatingHours
    }
}