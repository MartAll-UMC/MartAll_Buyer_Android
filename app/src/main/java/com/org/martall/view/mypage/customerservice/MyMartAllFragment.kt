package com.org.martall.view.mypage.customerservice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.databinding.FragmentDibsMartBinding
import com.org.martall.databinding.FragmentMyMartAllBinding

class MyMartAllFragment : Fragment() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var binding: FragmentMyMartAllBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyMartAllBinding.inflate(inflater, container, false)
        mainBinding = (requireActivity() as MainActivity).binding

        binding.likeProductBtn.setOnClickListener {
            mainBinding.bottomNavigationview.selectedItemId = R.id.menu_heart
        }

        binding.likeMartBtn.setOnClickListener {
            mainBinding.bottomNavigationview.selectedItemId = R.id.menu_heart

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