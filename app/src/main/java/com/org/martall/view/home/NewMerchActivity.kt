package com.org.martall.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.org.martall.adapter.CategoryRVAdapter
import com.org.martall.databinding.ActivityNewMerchBinding
import com.org.martall.model.dummyItems

class NewMerchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewMerchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMerchBinding.inflate(layoutInflater)
        binding.backIb.setOnClickListener {
            finish()
        }
        binding.productListRv.adapter = CategoryRVAdapter(dummyItems.subList(0, 12))

        setContentView(binding.root)
    }
}