package com.org.martall.view.home.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentNotificationEventBinding


class NotificationEventFragment:Fragment() {

    private lateinit var binding: FragmentNotificationEventBinding

    companion object {
        fun newInstance(): NotificationEventFragment {
            return NotificationEventFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationEventBinding.inflate(inflater, container, false)
        return binding.root
    }
}
