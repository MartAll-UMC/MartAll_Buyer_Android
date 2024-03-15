package com.org.martall.view.mypage.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentQueryBinding

/*
 1대 1 문의 Fragment
 */
class QueryFragment: Fragment() {

    private lateinit var binding : FragmentQueryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding= FragmentQueryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}