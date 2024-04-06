package com.org.martall

import CategoryFragment
import HomeFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.services.UserInfoManager
import com.org.martall.view.likelist.LikeFragment
import com.org.martall.view.login.LoginActivity
import com.org.martall.view.mypage.MyMartAllFragment
import com.org.martall.view.mart.MartFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val fragmentHome = HomeFragment()
    private val fragmentLocalStore = MartFragment()
    private val fragmentCategory = CategoryFragment()
    private val fragmentMyMartAll = MyMartAllFragment()
    private val fragmentHeart = LikeFragment()
    private lateinit var userInfoManager: UserInfoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfoManager = UserInfoManager(applicationContext)

        GlobalScope.launch {
            Log.d("[PRINT/TOKEN]", "${userInfoManager.getTokens()}")
            if (!userInfoManager.isValidToken()) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intent)
            } else {
                initBottomNavigation()
            }
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationview.selectedItemId = R.id.menu_home
        showFragment(fragmentHome)

        binding.bottomNavigationview.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.menu_home -> fragmentHome
                R.id.menu_localMart -> fragmentLocalStore
                R.id.menu_place -> fragmentCategory
                R.id.menu_heart -> fragmentHeart
                R.id.menu_user -> fragmentMyMartAll
                else -> fragmentHome
            }
            showFragment(fragment)
            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.menu_frame_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun selectBottomNavigationItem(itemId: Int) {
        binding.bottomNavigationview.selectedItemId = itemId
    }
}