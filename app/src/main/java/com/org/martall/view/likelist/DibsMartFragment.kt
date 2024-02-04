package com.org.martall.view.likelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.adapter.MartRVAdapter
import com.org.martall.databinding.FragmentDibsMartBinding
import com.org.martall.model.dummyData

class DibsMartFragment : Fragment() {
    lateinit var binding: FragmentDibsMartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDibsMartBinding.inflate(inflater, container, false)
        var likedMarts = dummyData.filter { it.isLiked }
        binding.groupRecyclerView.adapter = MartRVAdapter(likedMarts)

        if(likedMarts.isEmpty()) {
            binding.shopDibsLayout.root.visibility = View.VISIBLE
            binding.groupRecyclerView.visibility = View.GONE
        } else {
            binding.shopDibsLayout.root.visibility = View.GONE
            binding.groupRecyclerView.visibility = View.VISIBLE
        }

        return binding.root
    }
}