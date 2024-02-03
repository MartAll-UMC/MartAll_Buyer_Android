package com.org.martall.view.mypage.customerservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.org.martall.R
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}