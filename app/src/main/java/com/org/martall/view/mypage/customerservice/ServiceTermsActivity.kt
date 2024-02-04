package com.org.martall.view.mypage.customerservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.org.martall.R
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.databinding.ActivityServiceTermsBinding

class ServiceTermsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityServiceTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceTermsBinding.inflate(layoutInflater)
        binding.backIc.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}