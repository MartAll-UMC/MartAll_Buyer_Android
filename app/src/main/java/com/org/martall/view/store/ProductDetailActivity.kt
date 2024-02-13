package com.org.martall.view.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.org.martall.R
import com.org.martall.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ITEM_NAME = "extra_item_name"
    }

    private lateinit var binding : ActivityProductDetailBinding

    private var isHeartFilled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ItemName = intent.getIntExtra(EXTRA_ITEM_NAME, -1)
        if (ItemName != -1) {
            Log.d("ItemName", ItemName.toString())
        } else {
            Log.d("ItemName", ItemName.toString() + "전달 X")
        }

        binding.likeBtn.setOnClickListener {
            toggleHeart()
        }

        binding.backIc.setOnClickListener {
            finish()
        }
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