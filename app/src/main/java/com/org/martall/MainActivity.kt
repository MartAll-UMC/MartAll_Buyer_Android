package com.org.martall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.view.store.StoreActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        with(binding) {
            tvTemp.setOnClickListener {
                startActivity(Intent(applicationContext,StoreActivity::class.java))
            }
        }


    }
}