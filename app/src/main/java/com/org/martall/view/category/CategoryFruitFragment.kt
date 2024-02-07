package com.org.martall.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.CategoryRVAdapter
import com.org.martall.databinding.FragmentCategoryFruitBinding
import com.org.martall.model.dummyDailyItems
import com.org.martall.model.dummyFruitItems

class CategoryFruitFragment : Fragment() {

    private lateinit var binding: FragmentCategoryFruitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryFruitBinding.inflate(inflater, container, false)
        binding.rvProductList.adapter = CategoryRVAdapter(dummyFruitItems)
        return binding.root
    }

    companion object {
        fun newInstance(): CategoryFruitFragment {
            return CategoryFruitFragment()
        }
    }
}
