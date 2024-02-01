package com.org.martall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.view.mypage.customerservice.MyMartAllFragment
import com.org.martall.view.nearme.NearMeFragment
import com.org.martall.view.store.LocalMartFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fragmentNearMe = NearMeFragment()
    private var fragmentMyMartAll = MyMartAllFragment()

    private val fragmentLocalMart = LocalMartFragment()
    private var fragmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // 종혁님 코드
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}