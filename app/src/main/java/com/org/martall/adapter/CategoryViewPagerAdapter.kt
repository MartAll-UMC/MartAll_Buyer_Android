package com.org.martall.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.org.martall.view.home.category.CategoryAllFragment
import com.org.martall.view.home.category.CategoryFishFragment
import com.org.martall.view.home.category.CategoryFruitFragment
import com.org.martall.view.home.category.CategoryItemFragment
import com.org.martall.view.home.category.CategoryMeatFragment
import com.org.martall.view.home.category.CategorySnackFragment

class CategoryViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 6
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategoryAllFragment.newInstance()
            1 -> CategoryFruitFragment.newInstance()
            2 -> CategoryMeatFragment.newInstance()
            3 -> CategoryFishFragment.newInstance()
            4 -> CategorySnackFragment.newInstance()
            else -> CategoryItemFragment.newInstance()
        }
    }
}
