package com.org.martall.view.mypage.customerservice.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentQueryDetailBinding

class QueryDetailFragment : Fragment() {

    private lateinit var binding: FragmentQueryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQueryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}