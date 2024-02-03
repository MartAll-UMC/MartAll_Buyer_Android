package com.org.martall.view.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.org.martall.R
import com.org.martall.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProductDetailBinding

    private var isHeartFilled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        binding.likeBtn.setOnClickListener {
            toggleHeart()
        }

        setContentView(binding.root)
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
}