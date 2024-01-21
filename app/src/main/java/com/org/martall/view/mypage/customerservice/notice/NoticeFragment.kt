package com.org.martall.view.mypage.customerservice.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentNoticeBinding

/*
 공지 사항 Fragment
 */
class NoticeFragment : Fragment() {

    private lateinit var binding: FragmentNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNoticeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}