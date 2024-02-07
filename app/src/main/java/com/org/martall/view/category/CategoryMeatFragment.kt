package com.org.martall.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.CategoryRVAdapter
import com.org.martall.databinding.FragmentCategoryMeatBinding
import com.org.martall.model.dummyDailyItems
import com.org.martall.model.dummyMeatItems

class CategoryMeatFragment : Fragment() {

    private lateinit var binding: FragmentCategoryMeatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryMeatBinding.inflate(inflater, container, false)
        binding.rvProductList.adapter = CategoryRVAdapter(dummyMeatItems)
        return binding.root
    }

    companion object {
        fun newInstance(): CategoryMeatFragment {
            return CategoryMeatFragment()
        }
    }
}
