package com.org.martall.view.likelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.org.martall.databinding.FragmentLikeBinding
import com.org.martall.view.cart.CartActivity


class LikeFragment : Fragment() {
    private lateinit var binding: FragmentLikeBinding

    private val information = arrayListOf("찜한 상품", "단골 마트")

    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLikeBinding.inflate(inflater, container, false)

        binding.tbLikeLayout.icCart.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }

        val dibsAdapter = LikeVPAdapter(this)
        binding.vpLikeContent.adapter = dibsAdapter

        TabLayoutMediator(binding.tlLike, binding.vpLikeContent) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}