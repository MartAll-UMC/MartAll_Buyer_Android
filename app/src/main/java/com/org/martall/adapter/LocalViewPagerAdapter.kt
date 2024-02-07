package com.org.martall.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LocalViewPagerAdapter(fragmentActivity: Fragment) : FragmentStateAdapter(fragmentActivity) {
    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
        //TODO: notifyItemInserted!!
    }

    fun removeFragement() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
        //TODO: notifyItemRemoved!!
    }
}