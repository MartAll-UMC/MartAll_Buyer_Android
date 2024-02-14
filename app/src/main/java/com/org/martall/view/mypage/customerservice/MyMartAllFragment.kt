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
import com.org.martall.view.likelist.DibsFragment

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
            navigateToDibsFragment(0) // 0은 '찜한 상품' 탭을 나타냄
        }

        binding.likeMartBtn.setOnClickListener {
            mainBinding.bottomNavigationview.selectedItemId = R.id.menu_heart
            navigateToDibsFragment(1) // 1은 '단골 마트' 탭을 나타냄
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

    private fun navigateToDibsFragment(tabIndex: Int) {
        val dibsFragment = DibsFragment().apply {
            arguments = Bundle().apply {
                putInt("selectedTab", tabIndex)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.menu_frame_view, dibsFragment)
            .addToBackStack(null)
            .commit()
    }
}