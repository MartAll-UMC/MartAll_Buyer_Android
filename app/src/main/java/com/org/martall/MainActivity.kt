package com.org.martall

import CategoryFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.view.home.HomeFragment
import com.org.martall.view.mypage.customerservice.MyMartAllFragment
import com.org.martall.view.nearme.NearMeFragment
import com.org.martall.view.store.LocalMartFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fragmentHome = HomeFragment()
    private var fragmentCategory = CategoryFragment()
    private var fragmentNearMe = NearMeFragment()
    private var fragmentMyMartAll = MyMartAllFragment()
    private val fragmentLocalMart = LocalMartFragment()
    private var fragmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBottomNavigation()

        showFragment(fragmentHome)


    }

        private fun initBottomNavigation() {
            binding.bottomNavigationview.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> {
                        // 홈 화면으로 전환
                        showFragment(fragmentHome)
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_place -> {
                        // 카테고리 화면으로 전환
                        showFragment(fragmentCategory)
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }

        private fun showFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.menu_frame_view, fragment)
                .commitAllowingStateLoss()
        }
    }