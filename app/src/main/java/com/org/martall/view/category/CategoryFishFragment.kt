package com.org.martall.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.CategoryRVAdapter
import com.org.martall.databinding.FragmentCategoryFishBinding
import com.org.martall.model.dummyDailyItems

class CategoryFishFragment : Fragment() {

    private lateinit var binding: FragmentCategoryFishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryFishBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): CategoryFishFragment {
            return CategoryFishFragment()
        }
    }
}
