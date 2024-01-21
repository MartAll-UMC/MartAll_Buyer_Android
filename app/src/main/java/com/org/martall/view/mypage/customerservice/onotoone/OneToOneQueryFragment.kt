package com.org.martall.view.mypage.customerservice.onotoone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentOneToOneQueryBinding

/*
 1대 1 문의 Fragment
 */
class OneToOneQueryFragment: Fragment() {

    private lateinit var binding : FragmentOneToOneQueryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding= FragmentOneToOneQueryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}