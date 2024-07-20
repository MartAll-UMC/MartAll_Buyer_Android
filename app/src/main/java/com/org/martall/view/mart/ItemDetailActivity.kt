package com.org.martall.view.mart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.ViewModel.ProductDetailViewModel
import com.org.martall.databinding.ActivityItemDetailBinding
import com.org.martall.interfaces.CartApiInterface
import com.org.martall.models.FollowResponseDTO
import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.models.Mart
import com.org.martall.models.ProductDetailResponseDTO
import com.org.martall.models.Results
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MART_ID = "extra_mart_id"
        const val EXTRA_ITEM_ID = "extra_item_id"
    }

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var api: ApiService

    private var isHeartFilled = false
    private var isBookmarked: Boolean = false
    private var isLiked: Boolean = false

    private lateinit var martDetail: Mart
    private lateinit var productDetail: Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val martId = intent.getIntExtra(EXTRA_MART_ID, -1)
        val itemId = intent.getIntExtra(EXTRA_ITEM_ID, -1)

        binding.backIc.setOnClickListener {
            finish()
        }

        GlobalScope.launch {
            api = ApiService.createWithHeader(applicationContext)

            Log.d("ItemDetail", "martId: " + martId.toString() + "itemId: " + itemId.toString())
            loadMartData(martId = martId, itemId = itemId)
        }


        binding.cartBtn.setOnClickListener {
            GlobalScope.launch {
                api.addProductToCart(CartApiInterface.AddToCartBody(itemId = itemId))
                    .enqueue(object :
                        Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.code() == 200) {
                                Toast.makeText(
                                    this@ProductDetailActivity,
                                    "장바구니에 추가되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@ProductDetailActivity,
                                    "장바구니엔 한 가게의 상품만 담을 수 있습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.d("FailAddToCart", "장바구니 추가 실패")
                        }
                    })
            }
        }

        binding.bookmarkBtn.setOnClickListener {
            if (isBookmarked) {
                Log.d("follow", isBookmarked.toString())
                Log.d("follow", "언팔로우 요청")
                unfollowMart(martId)
            } else {
                Log.d("follow", isBookmarked.toString())
                Log.d("follow", "팔로우 요청")
                followMart(martId)
            }
        }

        binding.itemLikeBtn.setOnClickListener {

            isLiked = !isLiked
            updateLikedUI(isLiked)

            if (isLiked) {
                // 찜하기
                GlobalScope.launch {
                    api.likedItem(itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                        override fun onResponse(
                            call: Call<ItemLikedResponseDTO>,
                            response: Response<ItemLikedResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                                Log.d("[LIKE]", "Update request successful for item $isLiked")
                                Log.d(
                                    "[LIKE]",
                                    "Update request successful for item ${productDetail.like}"
                                )
                            } else {
                                Log.e("[LIKE]", "Failed to send update request for item $isLiked")
                                Log.d(
                                    "[LIKE]",
                                    "Update request successful for item ${productDetail.like}"
                                )

                            }
                        }

                        override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                            // 이전 상태로 되돌리기
                            isLiked = !isLiked
                            updateLikedUI(isLiked)
                            Log.e(
                                "[LIKE]",
                                "Failed to send update request for item $itemId"
                            )
                        }
                    })
                }
            } else {
                    // 찜 취소
                    api.unLikedItem(itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                        override fun onResponse(
                            call: Call<ItemLikedResponseDTO>,
                            response: Response<ItemLikedResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                // 성공 시 처리: 클릭 상태가 업데이트되었음을 로그로 출력
                                Log.d("[LIKE]", "Update request successful for item $isLiked")
                                Log.d("[LIKE]", "Update request successful for item ${productDetail.like}")

                            } else {
                                Log.d("[LIKE]", "Update request successful for item $isLiked")
                                Log.d("[LIKE]", "Update request successful for item ${productDetail.like}")
                            }
                        }

                        override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                            // 이전 상태로 되돌리기
                            isLiked = !isLiked
                            updateLikedUI(isLiked)
                            Log.e(
                                "[LIKE]",
                                "Failed to send update request for item $itemId"
                            )
                        }
                    })
                }
        }
    }

    private fun loadMartData(martId: Int, itemId: Int) {
        Log.d("[PRINT/loadMartData]", "martId: $martId, itemId: $itemId")

        api.getProductDetail(shopId = martId, itemId = itemId)
            .enqueue(object : Callback<ProductDetailResponseDTO> {
                override fun onResponse(
                    call: Call<ProductDetailResponseDTO>,
                    response: Response<ProductDetailResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        productDetail = response.body()?.result!!
                        isLiked = productDetail.like
                        Log.d("ProductDetail", productDetail.toString())

                        martDetail = productDetail?.mart!!
                        Log.d("ProductDetail", productDetail.toString())
                        Log.d("ProductDetail", productDetail.like.toString())

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
            itemNameTv.text = productDetail.itemName
            itemPriceTv.text = productDetail.price.toString()

        }
        Glide.with(this).load(productDetail.pic).into(binding.itemImgIv)
        Glide.with(this).load(productDetail.content).into(binding.itemDetailIv)
        updateLikedUI(productDetail.like)
    }

    private fun updateMartUI(martDetail: Mart) {
        with(binding) {
            martProfileIv.text = martDetail.martName
            martNameTv.text = martDetail.martName
            bookmarkCountTv.text = martDetail.bookmarkCount.toString()
            likeCountTv.text = martDetail.likeCount.toString()

            var hashTag = ""
            martDetail.martCategory?.forEach {
                hashTag += "#$it "
            }
            martHashtagTv.text = hashTag ?: "#음식"
        }
//        setCategories(martDetail.martCategory)
    }

    private fun updateLikedUI(isLiked: Boolean) {
        if (isLiked)
            binding.itemLikeBtn.setBackgroundResource(R.drawable.ic_heart_filled_20dp)
        else
            binding.itemLikeBtn.setBackgroundResource(R.drawable.ic_heart_unfilled_20dp)
    }


    private fun followMart(martId: Int) {
        GlobalScope.launch {
            api.followMart(martId).enqueue(object : Callback<FollowResponseDTO> {
                override fun onResponse(
                    call: Call<FollowResponseDTO>,
                    response: Response<FollowResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        // 성공적으로 팔로우한 경우
                        isBookmarked = true
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

    }

    private fun unfollowMart(martId: Int) {
        GlobalScope.launch {
            api.unfollowMart(martId).enqueue(object : Callback<FollowResponseDTO> {
                override fun onResponse(
                    call: Call<FollowResponseDTO>,
                    response: Response<FollowResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        // 성공적으로 팔로우한 경우
                        // isBookmarked = false
                        Log.d("[BOOKMARK]", "언팔로우 성공")
                        updateBookMarkUI()

                    } else {
                        Log.d("[BOOKMARK]", "언팔로우 실패")
                    }
                }

                override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                    Log.d("[BOOKMARK]", "언팔로우 통신 실패")
                }
            })
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun updateBookMarkUI() {
        Log.d("SuccessUpdateUI", "UI 업데이트")
        val buttonText = if (isBookmarked) "단골 취소" else "단골 추가"
        binding.bookmarkBtn.text = buttonText

        Log.d("BookMark", buttonText)
        Log.d("BookMark", isBookmarked.toString())

        val buttonColor =
            if (isBookmarked) R.drawable.background_white_r20 else R.drawable.background_primary400_r20
        binding.bookmarkBtn.setBackgroundResource(buttonColor)

        val buttonTextColor = if (isBookmarked) R.color.primary400 else R.color.white
        binding.bookmarkBtn.setTextColor(ContextCompat.getColor(this, buttonTextColor))
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