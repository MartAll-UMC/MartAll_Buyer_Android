package com.org.martall.view.home.notification

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.org.martall.MainActivity
import com.org.martall.adapter.NotificationViewPagerAdapter
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notificationArrowBackIb.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()  // 현재 액티비티를 종료하고 메인 액티비티로 이동
        }

        val viewPager: ViewPager2 = binding.notificationVp
        val tabs: TabLayout = binding.notificationTb
        val pagerAdapter = NotificationViewPagerAdapter(this)

        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "주문 현황"
                1 -> "이벤트"
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()
    }
}