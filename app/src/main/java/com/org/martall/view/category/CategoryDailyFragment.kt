package com.org.martall.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.CategoryRVAdapter
import com.org.martall.databinding.FragmentCategoryDailyBinding
import com.org.martall.model.dummyDailyItems

class CategoryDailyFragment : Fragment() {

    private lateinit var binding: FragmentCategoryDailyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryDailyBinding.inflate(inflater, container, false)
       /* binding.rvProductList.adapter = CategoryRVAdapter(dummyDailyItems)*/
        return binding.root
    }

    companion object {
        fun newInstance(): CategoryDailyFragment {
            return CategoryDailyFragment()
        }
    }
}
