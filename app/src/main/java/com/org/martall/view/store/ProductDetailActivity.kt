package com.org.martall.view.store

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.ViewModel.ProductDetailViewModel
import com.org.martall.databinding.ActivityProductDetailBinding
import com.org.martall.model.FollowResponseDTO
import com.org.martall.model.LikedItemListDTO
import com.org.martall.model.Mart
import com.org.martall.model.MartLikedResponseDTO
import com.org.martall.model.MartListResponseDTO
import com.org.martall.model.ProductDetailResponseDTO
import com.org.martall.model.Results
import com.org.martall.services.ApiServiceManager
import com.org.martall.services.CartApiServiceManager
import com.org.martall.services.ItemApiServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MART_ID = "extra_mart_id"
        const val EXTRA_ITEM_ID = "extra_item_id"
    }

//    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding : ActivityProductDetailBinding

    private var isHeartFilled = false
    private var isFollowing: Boolean = false
    private var isLiked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val martId = intent.getIntExtra(EXTRA_MART_ID, -1)
        val itemId = intent.getIntExtra(EXTRA_ITEM_ID, -1)

        Log.d("ProductDetail", "martId: " + martId.toString() + "itemId: " + itemId.toString())

        if (itemId != -1) {
            Log.d("ProductDetail", martId.toString() + itemId.toString())
        } else {
            Log.d("itemId", martId.toString() + itemId.toString() + "전달 X")
        }

        loadMartData(martId, itemId)

        binding.backIc.setOnClickListener {
            finish()
        }

        binding.bookmarkBtn.setOnClickListener {
            if (isFollowing) {
                // 언팔로우 요청
                Log.d("follow", isFollowing.toString())
                Log.d("follow", "언팔로우 요청")
                unfollowMart(martId)
            } else {
                // 팔로우 요청
                followMart(martId)
                Log.d("follow", isFollowing.toString())
                Log.d("follow", "팔로우 요청")
            }
        }

        binding.likeBtn.setOnClickListener {
            if(isLiked) {
                isLiked = !isLiked
                updateLikedUI(isLiked)

                // 찜 취소 서버 통신
                val apiService = ItemApiServiceManager.ItemapiService
                val call = apiService.unLikedItem(itemId = itemId)

                call.enqueue(object : Callback<MartLikedResponseDTO>{
                    override fun onResponse(
                        call: Call<MartLikedResponseDTO>,
                        response: Response<MartLikedResponseDTO>
                    ) {
                        Log.d("isLiked", "찜취소 서버 통신 성공")
                    }

                    override fun onFailure(call: Call<MartLikedResponseDTO>, t: Throwable) {
                        Log.d("isLiked", "찜취소 서버 통신 실패")
                    }

                })
            }
            else {
                isLiked = !isLiked
                updateLikedUI(isLiked)

                // 찜하기 서버 통신
                val apiService = ItemApiServiceManager.ItemapiService
                val call = apiService.likedItem(itemId = itemId)

                call.enqueue(object : Callback<MartLikedResponseDTO>{
                    override fun onResponse(
                        call: Call<MartLikedResponseDTO>,
                        response: Response<MartLikedResponseDTO>
                    ) {
                        Log.d("isLiked", "찜하기 서버 통신 성공")
                    }

                    override fun onFailure(call: Call<MartLikedResponseDTO>, t: Throwable) {
                        Log.d("isLiked", "찜하기 서버 통신 실패")
                    }

                })
            }
        }
    }

    private fun loadMartData(martId: Int, itemId: Int) {
        val apiService = CartApiServiceManager.CartapiService
        val call = apiService.getProductDetail(shopId = martId, itemId = itemId)

        call.enqueue(object : Callback<ProductDetailResponseDTO> {
            override fun onResponse(
                call: Call<ProductDetailResponseDTO>,
                response: Response<ProductDetailResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val productDetail = response.body()?.result
                    Log.d("ProductDetail", productDetail.toString())

                    val martDetail = productDetail?.mart
                    Log.d("ProductDetail", martDetail.toString())

                    isLiked = productDetail?.like ?: false

                    if (productDetail != null) {
                        updateProductUI(productDetail)
                    }

                    if (martDetail != null) {
                        updateMartUI(martDetail)
                    }
                } else {
                    Log.d("ProductDetail", "응답 실패")
                }
            }

            override fun onFailure(call: Call<ProductDetailResponseDTO>, t: Throwable) {
                Log.d("ProductDetail", "마트 전체 조회 연결 실패")
            }
        })
    }

    private fun updateProductUI(productDetail: Results) {
        with(binding) {
            productCategoryTv.text = productDetail.categoryName
            productNameTv.text = productDetail.itemName
            productPriceTv.text = productDetail.price.toString()
        }
    }

    private fun updateMartUI(martDetail: Mart) {
        with(binding) {
            martProfileIv.text = martDetail.martName
            martNameTv.text = martDetail.martName
            followerCountTv.text = martDetail.bookmarkCount.toString()
            likedCountTv.text = martDetail.likeCount.toString()
        }
        // setCategories(martDetail.martCategory)
    }

    private fun updateLikedUI(isLiked: Boolean) {
        if (isLiked)
            binding.likeBtn.setBackgroundResource(R.drawable.ic_heart_filled_20dp)
        else
            binding.likeBtn.setBackgroundResource(R.drawable.ic_heart_unfilled_20dp)
    }


    private fun followMart(martId: Int) {
        val apiService = ApiServiceManager.MartapiService
        val call = apiService.followMart(shopId = martId)

        call.enqueue(object : Callback<FollowResponseDTO> {
            override fun onResponse(
                call: Call<FollowResponseDTO>,
                response: Response<FollowResponseDTO>
            ) {
                if (response.isSuccessful) {
                    // 성공적으로 팔로우한 경우
                    isFollowing = true
                    Log.d("SuccessFollow", "팔로우 통신 성공")
                    updateBookMarkUI()

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
                response: Response<FollowResponseDTO>
            ) {
                if (response.isSuccessful) {
                    // 성공적으로 팔로우한 경우
                    isFollowing = false
                    Log.d("SuccessUnfollow", "언팔로우 성공")
                    updateBookMarkUI()

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
    private fun updateBookMarkUI() {
        Log.d("SuccessUpdateUI", "UI 업데이트")
        val buttonText = if (isFollowing) "단골 취소" else "단골 추가"
        binding.bookmarkBtn.text = buttonText

        Log.d("BookMark", buttonText)
        Log.d("BookMark", isFollowing.toString())

        val buttonColor = if (isFollowing) R.drawable.background_white_r20 else R.drawable.background_primary400_r20
        binding.bookmarkBtn.setBackgroundResource(buttonColor)

        val buttonTextColor = if (isFollowing) R.color.primary400 else R.color.white
        binding.bookmarkBtn.setTextColor(ContextCompat.getColor(this, buttonTextColor))
    }



    private fun toggleHeart() {
        isHeartFilled = !isHeartFilled
        val heartIcon = binding.likeBtn

        if (isHeartFilled) {
            heartIcon.setImageResource(R.drawable.ic_heart_filled_20dp)
        } else {
            heartIcon.setImageResource(R.drawable.ic_heart_unfilled_20dp)
        }
    }

    // 카테고리 동적 생성
    private fun setCategories(categories: List<String>) {

        val linearLayout: LinearLayout = binding.martHashtagListTv
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
            marginParams.setMargins(binding.root.context.resources.getDimensionPixelSize(R.dimen.margin_right), 0, 0, 0)
            textView.layoutParams = marginParams

            linearLayout.addView(textView)
        }
    }
}