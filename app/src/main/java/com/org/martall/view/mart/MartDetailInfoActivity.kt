package com.org.martall.view.mart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.org.martall.R
import com.org.martall.ViewModel.SharedMartViewModel
import com.org.martall.adapter.MartDetailRVAdapter
import com.org.martall.databinding.ActivityMartDetailInfoBinding
import com.org.martall.models.FollowResponseDTO
import com.org.martall.models.MartDataDTO
import com.org.martall.models.MartListResponseDTO
import com.org.martall.services.ApiService
import com.org.martall.services.ApiServiceManager
import com.org.martall.view.mart.user.bottomsheet.DetailBottomSheet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartDetailInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMartDetailInfoBinding
    private lateinit var api: ApiService

    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMartDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 클릭된 아이템의 martId
        val martId = intent.getIntExtra("martId", -1)
        Log.d("MartDetail", martId.toString())

        loadMartData(martId)

        binding.backIc.setOnClickListener {
            finish()
        }

        binding.showDetailBtn.setOnClickListener {
            val detailBottomSheet = DetailBottomSheet.newInstance(martId)
            Log.d("BottomSheet", martId.toString())
            detailBottomSheet.show(supportFragmentManager, null)
        }

        binding.addBookmarkBtn.setOnClickListener {
            if (isBookmarked) {
                // 언팔로우 요청
                Log.d("follow", isBookmarked.toString())
                Log.d("follow", "언팔로우 요청")
                unfollowMart(martId)
            } else {
                // 팔로우 요청
                followMart(martId)
                Log.d("follow", isBookmarked.toString())
                Log.d("follow", "팔로우 요청")
            }
        }
    }


    private fun loadMartData(martId: Int) {
        GlobalScope.launch {
            api = ApiService.createWithHeader(applicationContext)

            api.ShowAllShops(
                tag = null, minBookmark = null, maxBookmark = null,
                minLike = null, maxLike = null, sort = null
            ).enqueue(object : Callback<MartListResponseDTO> {
                override fun onResponse(
                    call: Call<MartListResponseDTO>,
                    response: Response<MartListResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        val martList = response.body()?.result ?: emptyList()

                        // 특정 martId의 데이터만 필터링
                        val selectedMart = martList.find { it.martId == martId }

                        // 데이터 설정
                        selectedMart?.let {

                            updateMartDetailUI(selectedMart)
                            updateMartDetailProduct(selectedMart)
                        }
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

    private fun followMart(martId: Int) {
        val apiService = ApiServiceManager.MartapiService
        val call = apiService.followMart(shopId = martId)

        call.enqueue(object : Callback<FollowResponseDTO> {
            override fun onResponse(
                call: Call<FollowResponseDTO>,
                response: Response<FollowResponseDTO>,
            ) {
                if (response.isSuccessful) {
                    // 성공적으로 팔로우한 경우
                    isBookmarked = true
                    Log.d("SuccessFollow", "팔로우 통신 성공")
                    updateFollowUI(isBookmarked)

                } else {
                    Log.d("FailFollow", "통신 실패")
                }
            }

            override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                Log.d("check", "마트 전체 조회 연결 실패")
            }
        })
    }

    private fun unfollowMart(martId: Int) {
        val apiService = ApiServiceManager.MartapiService
        val call = apiService.unfollowMart(shopId = martId)

        call.enqueue(object : Callback<FollowResponseDTO> {
            override fun onResponse(
                call: Call<FollowResponseDTO>,
                response: Response<FollowResponseDTO>,
            ) {
                if (response.isSuccessful) {
                    // 성공적으로 팔로우한 경우
                    isBookmarked = false
                    Log.d("SuccessUnfollow", "언팔로우 성공")
                    updateFollowUI(isBookmarked)

                } else {
                    Log.d("FailFollow", "통신 실패")
                }
            }

            override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                Log.d("check", "마트 전체 조회 연결 실패")
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun updateFollowUI(isBookmarked: Boolean) {
        Log.d("SuccessUpdateUI", "UI 업데이트")
        val buttonText = if (isBookmarked) "단골 취소" else "단골 추가"
        binding.addBookmarkBtn.text = buttonText

        val buttonColor =
            if (isBookmarked) R.drawable.background_primary400_r12 else R.drawable.background_primary400_fill_r12
        binding.addBookmarkBtn.setBackgroundResource(buttonColor)

        val buttonTextColor = if (isBookmarked) R.color.primary400 else R.color.white
        binding.addBookmarkBtn.setTextColor(ContextCompat.getColor(this, buttonTextColor))
    }


    private fun updateMartDetailUI(selectedMart: MartDataDTO) {
        binding.martNameTv.text = selectedMart.name
        Log.d("selectedMart", selectedMart.name)
        binding.bookmarkCountTv.text = selectedMart.followersCount.toString()
        binding.likeCountTv.text = selectedMart.likeCount.toString()
        binding.martProfileIv.text = selectedMart.name
        setCategories(selectedMart.categories)

        isBookmarked = selectedMart.bookmarkYn
        updateFollowUI(selectedMart.bookmarkYn)
    }

    private fun updateMartDetailProduct(martProduct: MartDataDTO) {
        val martDetailRVAdapter = MartDetailRVAdapter(martProduct, api)
        val layoutManager = GridLayoutManager(this, 2)

        binding.martDetailRecyclerview.layoutManager = layoutManager
        binding.martDetailRecyclerview.adapter = martDetailRVAdapter

        // 아이템 클릭 리스너 설정
        martDetailRVAdapter.setOnItemClickListener(object :
            MartDetailRVAdapter.OnItemClickListener {
            override fun onItemClick(martId: Int, itemId: Int) {
                // 아이템 클릭 시 호출되는 메서드
                val intent = Intent(this@MartDetailInfoActivity, ProductDetailActivity::class.java)
                intent.putExtra(ProductDetailActivity.EXTRA_ITEM_ID, itemId)
                intent.putExtra(ProductDetailActivity.EXTRA_MART_ID, martId)
                startActivity(intent)
            }
        })
    }

    // 카테고리 개수에 따른 동적 생성
    private fun setCategories(categories: List<String>) {

        val linearLayout: LinearLayout = binding.martCategoriesLayout
        linearLayout.removeAllViews()

        for (category in categories) {
            val textView = TextView(binding.root.context)
            textView.text = "#$category"
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey400))
            textView.setTextAppearance(R.style.sRegular)

            // Add margin to TextViews
            val marginParams = LinearLayout.LayoutParams(textView.layoutParams)
            marginParams.setMargins(
                binding.root.context.resources.getDimensionPixelSize(R.dimen.margin_right),
                0,
                0,
                0
            )
            textView.layoutParams = marginParams

            linearLayout.addView(textView)
        }
    }
}