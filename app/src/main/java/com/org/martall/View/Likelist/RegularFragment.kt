package com.org.martall.View.Likelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentRegularBinding

class RegularFragment : Fragment() {
    lateinit var binding : FragmentRegularBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegularBinding.inflate(inflater,container,false)

        return binding.root
    }

}