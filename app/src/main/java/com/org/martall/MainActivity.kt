package com.org.martall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.view.home.HomeFragment
import com.org.martall.view.mypage.customerservice.MyMartAllFragment
import com.org.martall.view.nearme.NearMeFragment
import com.org.martall.view.store.LocalStoreFragment
import com.org.martall.view.store.MartDetailInfoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var fragmentNearMe = NearMeFragment()
    private var fragmentMyMartAll = MyMartAllFragment()
    private var fragmentHome = HomeFragment()
    private val fragmentLocalStore = LocalStoreFragment()
    private var fragmentManager: FragmentManager = supportFragmentManager

    // 상품 상세 확인
    private var fragmentMartDetailInfo = MartDetailInfoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigationview)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            val transaction = fragmentManager.beginTransaction()

            when (menuItem.itemId) {
                R.id.menu_home -> transaction.replace(R.id.menu_frame_view, fragmentHome)
                    .commitAllowingStateLoss()

                R.id.menu_localMart -> transaction.replace(R.id.menu_frame_view, fragmentLocalStore)
                    .commitAllowingStateLoss()

                R.id.menu_place -> transaction.replace(R.id.menu_frame_view, fragmentNearMe)
                    .commitAllowingStateLoss()
                R.id.menu_heart -> transaction.replace(R.id.menu_frame_view, fragmentMartDetailInfo)
                    .commitAllowingStateLoss()
                R.id.menu_user -> transaction.replace(R.id.menu_frame_view, fragmentMyMartAll)
                    .commitAllowingStateLoss()
            }
            true
        }
    }
}