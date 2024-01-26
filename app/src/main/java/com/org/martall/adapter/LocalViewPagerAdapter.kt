package com.org.martall.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.org.martall.view.store.LocalMartFragment

class LocalViewPagerAdapter(fragmentActivity: LocalMartFragment) :

    FragmentStateAdapter(fragmentActivity) {

    private lateinit var LocalViewPagerAdapter: LocalViewPagerAdapter
    var fragments: ArrayList<Fragment> = ArrayList()

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