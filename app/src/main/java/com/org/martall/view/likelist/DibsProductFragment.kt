package com.org.martall.view.likelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.CategoryRVAdapter
import com.org.martall.databinding.FragmentDibsProductBinding
import com.org.martall.model.dummyItems

class DibsProductFragment : Fragment() {
    lateinit var binding : FragmentDibsProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDibsProductBinding.inflate(inflater,container, false)
        var likedItems = dummyItems.filter { it.isLiked }
        binding.rvProductList.adapter = CategoryRVAdapter(likedItems)

        if(likedItems.isEmpty()) {
            binding.shopDibsLayout.root.visibility = VISIBLE
            binding.rvProductList.visibility = View.GONE
        } else {
            binding.shopDibsLayout.root.visibility = View.GONE
            binding.rvProductList.visibility = VISIBLE
        }

        return binding.root
    }
}