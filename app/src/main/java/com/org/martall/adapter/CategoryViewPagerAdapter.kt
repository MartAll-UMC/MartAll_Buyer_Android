package com.org.martall.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.org.martall.view.category.CategoryInnerFragment

class CategoryViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategoryInnerFragment.newInstance("전체")
            1 -> CategoryInnerFragment.newInstance("과일채소")
            2 -> CategoryInnerFragment.newInstance("정육")
            3 -> CategoryInnerFragment.newInstance("수산")
            4 -> CategoryInnerFragment.newInstance("간식")
            else -> CategoryInnerFragment.newInstance("생활용품")

//            0 -> CategoryAllFragment.newInstance()
//            1 -> CategoryFruitFragment.newInstance()
//            2 -> CategoryMeatFragment.newInstance()
//            3 -> CategoryFishFragment.newInstance()
//            4 -> CategorySnackFragment.newInstance()
//            else -> CategoryDailyFragment.newInstance()
        }
    }
}
