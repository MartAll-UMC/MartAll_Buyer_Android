package com.org.martall.view.store

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
import com.org.martall.view.store.user.bottomsheet.DetailBottomSheet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartDetailInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMartDetailInfoBinding
    private lateinit var api: ApiService
    private val sharedMartViewModel: SharedMartViewModel by viewModels()

    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMartDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val selectedMartLiveData: LiveData<MartDataDTO> = sharedMartViewModel.getSelectedMart()

        selectedMartLiveData.observe(this) {
            it?.let { selectedMart ->
                updateUI(selectedMart)
            }
        }

         */

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

        binding.addFavoriteMartBtn.setOnClickListener {
            isBookmarked = !isBookmarked
            updateFollowUI(isBookmarked)

            GlobalScope.launch {
                if (isBookmarked) {
                    // 팔로우
                    api.followMart(martId).enqueue(object: Callback<FollowResponseDTO> {
                        override fun onResponse(
                            call: Call<FollowResponseDTO>,
                            response: Response<FollowResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                Log.d("[PRINT/MART]", "마트 팔로우 성공")

                            } else {
                                // 서버 오류 발생
                                // Handle server error
                            }
                        }

                        override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                            // 통신 실패
                            // Handle failure
                            Log.d("check", "마트 팔로우 실패")
                        }
                    })

                } else {
                    // 언팔로우 요청
                    api.unfollowMart(martId).enqueue(object: Callback<FollowResponseDTO> {
                        override fun onResponse(
                            call: Call<FollowResponseDTO>,
                            response: Response<FollowResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                Log.d("[PRINT/MART]", "마트 팔로우 취소 성공")

                            } else {
                                // 서버 오류 발생
                                // Handle server error
                            }
                        }

                        override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                            // 통신 실패
                            // Handle failure
                            Log.d("check", "마트 팔로우 실패")
                        }
                    })
                }
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

                        // 특정 martId의 데이터만 필터링하여 가져오기 (예시에서는 전체 데이터를 그대로 사용)
                        val selectedMart = martList.find { it.martId == martId }

                        // 데이터 설정
                        selectedMart?.let {
                            updateMartDetailUI(selectedMart)
                            updateMartDetailProduct(selectedMart)
                        }

//                    val martProduct = selectedMart?.items
//                    martProduct?.let {
//                        if (martName != null) {
//                            updateMartDetailProduct(martProduct, martName)
//                        }
//                    }
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

    @SuppressLint("ResourceAsColor")
    private fun updateFollowUI(isBookmarked: Boolean) {
        Log.d("SuccessUpdateUI", "UI 업데이트")
        val buttonText = if (isBookmarked) "단골 취소" else "단골 추가"
        binding.addFavoriteMartBtn.text = buttonText

        val buttonColor =
            if (isBookmarked) R.drawable.background_primary400_r12 else R.drawable.background_primary400_fill_r12
        binding.addFavoriteMartBtn.setBackgroundResource(buttonColor)

        val buttonTextColor = if (isBookmarked) R.color.primary400 else R.color.white
        binding.addFavoriteMartBtn.setTextColor(ContextCompat.getColor(this, buttonTextColor))
    }


    private fun updateMartDetailUI(selectedMart: MartDataDTO) {
        binding.martNameTv.text = selectedMart.name
        Log.d("selectedMart", selectedMart.name)
        binding.followerCountTv.text = selectedMart.followersCount.toString()
        binding.visitorCountTv.text = selectedMart.likeCount.toString()
        binding.martPlaceTv.text = selectedMart.location
        binding.martProfileIv.text = selectedMart.name
        // Glide.with(this).load(selectedMart.imageUrl).into(binding.martProfileIv)
        setCategories(selectedMart.categories)
    }

    private fun updateMartDetailProduct(martProduct: MartDataDTO) {
        val martDetailRVAdapter = MartDetailRVAdapter(martProduct)
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

    // 카테고리 동적 생성
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