package com.org.martall.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.org.martall.R
import com.org.martall.adapter.HomeAdViewPagerAdapter
import com.org.martall.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.menuIb.setOnClickListener {
            val intent = Intent(context, com.org.martall.view.home.notification.NotificationActivity::class.java)
            startActivity(intent)
        }

        val adAdapter = HomeAdViewPagerAdapter(this)
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_1_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_2_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_3_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_4_360dp))
        binding.homeAdVp.adapter = adAdapter
        binding.homeAdVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}
