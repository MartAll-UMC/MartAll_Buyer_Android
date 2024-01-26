package com.org.martall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.view.store.LocalMartFragment
import com.org.martall.view.store.StoreActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentLocalMart = LocalMartFragment()
    private var fragmentManager: FragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // 종혁님 코드
        binding=ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        with(binding) {
            tvTemp.setOnClickListener {
                startActivity(Intent(applicationContext,StoreActivity::class.java))
            }
        }

        // 내 코드

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigationview)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            val transaction = fragmentManager.beginTransaction()

            when (menuItem.itemId) {
//                R.id.menu_home -> transaction.replace(R.id.menu_frame_view, fragmentHome)
//                    .commitAllowingStateLoss()
                R.id.menu_localMart -> transaction.replace(R.id.menu_frame_view, fragmentLocalMart)
                    .commitAllowingStateLoss()
//                R.id.menu_place -> transaction.replace(R.id.menu_frame_view, fragmentPlace)
//                    .commitAllowingStateLoss()
//                R.id.menu_heart -> transaction.replace(R.id.menu_frame_view, fragmentHeart)
//                    .commitAllowingStateLoss()
//                R.id.menu_user -> transaction.replace(R.id.menu_frame_view, fragmentMenu)
//                    .commitAllowingStateLoss()
            }
            true
        }
    }
}