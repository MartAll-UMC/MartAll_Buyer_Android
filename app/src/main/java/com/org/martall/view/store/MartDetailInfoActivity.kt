package com.org.martall.view.store

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.org.martall.R
import com.org.martall.databinding.ActivityMartDetailInfoBinding
import com.org.martall.view.store.user.bottomsheet.DetailBottomSheet
import com.org.martall.view.store.user.bottomsheet.SortBottomSheet

class MartDetailInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMartDetailInfoBinding

    private var isHeartFilled1 = false
    private var isHeartFilled2 = false
    private var isHeartFilled3 = false
    private var isHeartFilled4 = false

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMartDetailInfoBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

//        binding.showDetailBtn.setOnClickListener {
//            showDetailBottomSheet()
//        }
//
//        showSortBottomSheet()

        binding.backIc.setOnClickListener {
            finish()
        }

        binding.post1.setOnClickListener {
            // 상품 상세 페이지로 이동
            val intent = Intent(this, ProductDetailActivity::class.java)
            startActivity(intent)
        }

        binding.itemMartPostHeart1Iv.setOnClickListener {
            isHeartFilled1 = !isHeartFilled1
            val heartIcon = binding.itemMartPostHeart1Iv

            if (isHeartFilled1) {
                heartIcon.setImageResource(R.drawable.ic_heart_filled_20dp)
            } else {
                heartIcon.setImageResource(R.drawable.white_heart_ic)
            }
        }

        binding.itemMartPostHeart2Iv.setOnClickListener {
            isHeartFilled2 = !isHeartFilled2
            val heartIcon = binding.itemMartPostHeart2Iv

            if (isHeartFilled2) {
                heartIcon.setImageResource(R.drawable.ic_heart_filled_20dp)
            } else {
                heartIcon.setImageResource(R.drawable.white_heart_ic)
            }
        }

        binding.itemMartPostHeart3Iv.setOnClickListener {
            isHeartFilled3 = !isHeartFilled3
            val heartIcon = binding.itemMartPostHeart3Iv

            if (isHeartFilled3) {
                heartIcon.setImageResource(R.drawable.ic_heart_filled_20dp)
            } else {
                heartIcon.setImageResource(R.drawable.white_heart_ic)
            }
        }

        binding.itemMartPostHeart4Iv.setOnClickListener {
            isHeartFilled4 = !isHeartFilled4
            val heartIcon = binding.itemMartPostHeart4Iv

            if (isHeartFilled4) {
                heartIcon.setImageResource(R.drawable.ic_heart_filled_20dp)
            } else {
                heartIcon.setImageResource(R.drawable.white_heart_ic)
            }
        }


        binding.addFavoriteMartBtn.setOnClickListener {
            toggleFavoriteButton()
        }
    }

//    private fun showDetailBottomSheet() {
//        DetailBottomSheet().show(
//            childFragmentManager,
//            null
//        )
//    }
//
//    private fun showSortBottomSheet() {
//        binding.sortTv.setOnClickListener {
//            SortBottomSheet().show(
//                childFragmentManager,
//                null
//            )
//        }
//    }

    @SuppressLint("ResourceAsColor")
    fun toggleFavoriteButton() {
        isFavorite = !isFavorite

        if (isFavorite) {
            binding.addFavoriteMartBtn.text = "단골가게"
            binding.addFavoriteMartBtn.setBackgroundResource(R.drawable.background_primary400_r12)
            binding.addFavoriteMartBtn.setTextColor(R.color.primary400)
        } else {
            binding.addFavoriteMartBtn.text = "단골추가"
            binding.addFavoriteMartBtn.setTextColor(R.color.white)
            binding.addFavoriteMartBtn.setBackgroundResource(R.drawable.background_primary400_fill_r12)
        }
    }

    /*
    fun toggleHeart() {
        isHeartFilled = !isHeartFilled
        val heartIcon = binding.itemMartPostHeart1Iv

        if (isHeartFilled) {
            heartIcon.setImageResource(R.drawable.ic_heart_filled_20dp)
        } else {
            heartIcon.setImageResource(R.drawable.white_heart_ic)
        }
    }
     */
}