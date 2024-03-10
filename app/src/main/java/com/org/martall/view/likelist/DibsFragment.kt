package com.org.martall.view.likelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.org.martall.databinding.FragmentDibsBinding
import com.org.martall.view.cart.CartActivity


class DibsFragment : Fragment() {
    private lateinit var binding: FragmentDibsBinding

    private val information = arrayListOf("찜한 상품", "단골 마트")

    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDibsBinding.inflate(inflater, container, false)

        binding.shopDibsLayout.cartIc.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }

        val dibsAdapter = DibsVPAdapter(this)
        binding.dibsContentVp.adapter = dibsAdapter

        TabLayoutMediator(binding.dibsContentTb, binding.dibsContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}