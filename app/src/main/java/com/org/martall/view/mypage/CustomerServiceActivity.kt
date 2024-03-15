package com.org.martall.view.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.org.martall.databinding.ActivityCustomerServiceBinding

/*
* 고객 센터 3개의 Fragment 를 담기 위해 만든 Activity  */

class CustomerServiceActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCustomerServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCustomerServiceBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}