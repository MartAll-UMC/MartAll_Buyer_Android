package com.org.martall.view.home.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentNotificationOrderBinding

class NotificationOrderFragment : Fragment() {

    private lateinit var binding: FragmentNotificationOrderBinding

    companion object {
        fun newInstance(): NotificationOrderFragment {
            return NotificationOrderFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationOrderBinding.inflate(inflater, container, false)
        return binding.root
    }
}