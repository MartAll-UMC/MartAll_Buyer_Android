package com.org.martall.view.home.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentCategoryItemBinding

class CategoryItemFragment : Fragment() {

    private lateinit var binding: FragmentCategoryItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): CategoryItemFragment {
            return CategoryItemFragment()
        }
    }
}
