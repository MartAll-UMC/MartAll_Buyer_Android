package com.org.martall.view.mypage.customerservice.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentNoticeDetailBinding

class NoticeDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoticeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNoticeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}