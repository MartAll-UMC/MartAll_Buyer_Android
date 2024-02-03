package com.org.martall.view.mypage.customerservice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.org.martall.view.likelist.DibsActivity
import com.org.martall.databinding.FragmentMyMartAllBinding

class MyMartAllFragment : Fragment() {
    private lateinit var binding : FragmentMyMartAllBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMartAllBinding.inflate(inflater, container, false)

        binding.likeGoodsBtn.setOnClickListener {
            val intent = Intent(requireContext(),DibsActivity::class.java)
            startActivity(intent)
        }

        binding.likeGoodsBtn.setOnClickListener{
            val intent = Intent(requireActivity(), DibsActivity::class.java)
            Log.d("intent", "넘어감")
            startActivity(intent)
        }

        binding.privacyPolicyPannel.setOnClickListener {
            val intent = Intent(requireContext(), PrivacyPolicyActivity::class.java)
            Log.d("intent", "넘어감")
            startActivity(intent)
        }

        binding.serviceTermPannel.setOnClickListener {
            val intent = Intent(requireContext(), ServiceTermsActivity::class.java)
            Log.d("intent", "넘어감")
            startActivity(intent)
        }

        return binding.root
    }
}