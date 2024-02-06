package com.org.martall.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.adapter.HomeAdViewPagerAdapter
import com.org.martall.adapter.HomeMartRVAdapter
import com.org.martall.adapter.ProductSimpleRVAdapter
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.databinding.FragmentHomeBinding
import com.org.martall.model.dummyData
import com.org.martall.model.dummyItems
import com.org.martall.view.search.SearchActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mainBinding = (requireActivity() as MainActivity).binding

        binding.tbHome.btnAlarm.setOnClickListener {
            val intent = Intent(
                context,
                com.org.martall.view.home.notification.NotificationActivity::class.java
            )
            startActivity(intent)
        }

        val adAdapter = HomeAdViewPagerAdapter(this)
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_1_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_2_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_3_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_4_360dp))
        binding.homeAdVp.adapter = adAdapter
        binding.homeAdVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homeSearchTv.setOnClickListener {
            startActivity(Intent(context, SearchActivity::class.java))
        }

        binding.homeMerchandiseRv.adapter = ProductSimpleRVAdapter(dummyItems.subList(0, 4))
        binding.homeRecommendRv.adapter = HomeMartRVAdapter(dummyData.subList(0, 4))

        binding.homeMerchandiseMoreTv.setOnClickListener {
            startActivity(Intent(context, NewMerchActivity::class.java))
        }

        binding.homeMartMoreTv.setOnClickListener {
            mainBinding.bottomNavigationview.selectedItemId = R.id.menu_localMart
        }

        return binding.root
    }
}
