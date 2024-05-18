package com.org.martall.view.login

import androidx.appcompat.app.AppCompatActivity
import com.org.martall.databinding.ActivityMartAllLoginBinding
import android.os.Bundle
import com.org.martall.R
import com.org.martall.databinding.ActivitySignUpBinding

class MartAllLoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMartAllLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}