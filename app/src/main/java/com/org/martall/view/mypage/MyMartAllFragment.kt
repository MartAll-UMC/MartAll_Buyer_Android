package com.org.martall.view.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.org.martall.BuildConfig
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.databinding.ActivityMainBinding
import com.org.martall.databinding.FragmentMyMartAllBinding
import com.org.martall.models.UserResponseDTO
import com.org.martall.services.ApiService
import com.org.martall.services.UserInfoManager
import com.org.martall.view.likelist.LikeFragment
import com.org.martall.view.login.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMartAllFragment : Fragment() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var binding: FragmentMyMartAllBinding
    private lateinit var api: ApiService
    private lateinit var userInfoManager: UserInfoManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyMartAllBinding.inflate(inflater, container, false)
        mainBinding = (requireActivity() as MainActivity).binding

        userInfoManager = UserInfoManager(requireContext())
        init()

        binding.tbMypage.ivCart.setOnClickListener {
            val intent = Intent(context, com.org.martall.view.cart.CartActivity::class.java)
            startActivity(intent)
        }

        binding.tbMypage.btnAlarm.setOnClickListener {
            val intent = Intent(
                context,
                com.org.martall.view.home.notification.NotificationActivity::class.java
            )
            startActivity(intent)
        }

        binding.helpCenterBtn.setOnClickListener {
            val url = BuildConfig.KAKAO_CHANNEL_URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

        }

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

        binding.logoutBtn.setOnClickListener {
            logout()
        }

        binding.quitBtn.setOnClickListener {
            withdraw()
        }

        return binding.root
    }

    private fun navigateToDibsFragment(tabIndex: Int) {
        val dibsFragment = LikeFragment().apply {
            arguments = Bundle().apply {
                putInt("selectedTab", tabIndex)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.menu_frame_view, dibsFragment)
            .commit()
    }

    private fun init() {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getUserProfile().enqueue(object : Callback<UserResponseDTO> {
                override fun onResponse(
                    call: Call<UserResponseDTO>,
                    response: Response<UserResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()?.result!!
                        Glide.with(requireContext()).load(user.imgUrl).into(binding.profileIv)
                        binding.userNameTv.text = user.nickname
                    }
                }

                override fun onFailure(call: Call<UserResponseDTO>, t: Throwable) {
                    Log.e("Retrofit", "Retrofit call failed with message: ${t.message}")
                }
            })
        }
    }

    private fun logout() {
        GlobalScope.launch {
            userInfoManager.updateTokens("", "", "", "")
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun withdraw() {
        logout()
//        GlobalScope.launch {
//            api.withdraw().enqueue(object : Callback<Unit> {
//                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                    if (response.isSuccessful) {
//                        GlobalScope.launch {
//                            userInfoManager.updateTokens("", "", "", "")
//                            val intent = Intent(requireContext(), LoginActivity::class.java)
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                            startActivity(intent)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<Unit>, t: Throwable) {
//                    Log.e("Retrofit", "Retrofit call failed with message: ${t.message}")
//                }
//            })
//        }
    }
}