package com.org.martall.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.org.martall.view.home.notification.NotificationEventFragment
import com.org.martall.view.home.notification.NotificationOrderFragment

class NotificationViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NotificationOrderFragment.newInstance()
            else -> NotificationEventFragment.newInstance()
        }
    }
}
