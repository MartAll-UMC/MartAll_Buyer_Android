package com.org.martall.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.CategoryRVAdapter
import com.org.martall.databinding.FragmentCategoryAllBinding
import com.org.martall.model.dummyItems

class CategoryAllFragment : Fragment() {

    private lateinit var binding: FragmentCategoryAllBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryAllBinding.inflate(inflater, container, false)
  /*      binding.rvProductList.adapter = CategoryRVAdapter(dummyItems)*/

        return binding.root
    }

    companion object {
        fun newInstance(): CategoryAllFragment {
            return CategoryAllFragment()
        }
    }
}