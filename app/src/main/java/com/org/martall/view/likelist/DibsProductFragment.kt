package com.org.martall.view.likelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentDibsProductBinding

class DibsProductFragment : Fragment() {
    lateinit var binding : FragmentDibsProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDibsProductBinding.inflate(inflater,container, false)
        return binding.root
    }

}