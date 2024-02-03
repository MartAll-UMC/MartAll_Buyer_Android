package com.org.martall.view.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.org.martall.adapter.LocalViewPagerAdapter
import com.org.martall.databinding.FragmentLocalMartBinding

class LocalMartFragment : Fragment() {
    private lateinit var binding : FragmentLocalMartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocalMartBinding.inflate(inflater, container, false)

        initViewPager()

        return binding.root
    }

    private fun initViewPager() {

        //ViewPager2 Adapter 셋팅
        var localViewPagerAdapter = LocalViewPagerAdapter(this)
        localViewPagerAdapter.addFragment(LocalStoreFragment())

        //Adapter 연결
        binding.localMartViewPager.apply {
            adapter = localViewPagerAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        binding.localMartViewPager.adapter = localViewPagerAdapter

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.localMartTabLayout, binding.localMartViewPager) { tab, position ->
            Log.e("YMC", "ViewPager position: ${position}")
            when (position) {
                0 -> tab.text = "마트"
            }
        }.attach()
    }
}