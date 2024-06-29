package com.org.martall.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.org.martall.databinding.ActivityMartAllLoginBinding
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.databinding.ActivitySignUpBinding
import com.org.martall.models.LoginRequest
import com.org.martall.models.LoginResponse
import com.org.martall.models.MartAllLogInRequest
import com.org.martall.models.MartAllLogInResponse
import com.org.martall.services.ApiService
import com.org.martall.services.MartAllUserInfoManager
import com.org.martall.services.UserInfoManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartAllLoginActivity : AppCompatActivity() {

    private var id: String = ""
    private var password: String = ""

    private lateinit var api: ApiService
    private lateinit var userInfoManager: MartAllUserInfoManager


    private val binding by lazy { ActivityMartAllLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        api = ApiService.createMock()
        userInfoManager = MartAllUserInfoManager(applicationContext)


        binding.loginBtn.setOnClickListener {
            signIn()
        }

        binding.signupTv.setOnClickListener {
            val intent = Intent(this@MartAllLoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        var isIdValid: Boolean = false
        var isPasswordValid: Boolean = false

        id = binding.loginIdEt.text.toString().trim()
        password = binding.loginPasswordEt.text.toString().trim()

        // id 검증
        if (TextUtils.isEmpty(id)) {
            binding.loginIdEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            isIdValid = false
        } else {
            binding.loginIdEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            isIdValid = true
        }

        // 비밀번호 검증
        if (TextUtils.isEmpty(password)) {
            binding.loginPasswordEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            isPasswordValid = false
        } else {
            binding.loginPasswordEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            isPasswordValid = true
        }

        if (isIdValid && isPasswordValid) {
            // val request = MartAllLogInRequest(id, password)
            // Log.d("[LOGIN]", request.toString())

            api.logIn(
                MartAllLogInRequest(
                    id = id,
                    password = password
                )
            ).enqueue(object : Callback<MartAllLogInResponse> {
                override fun onResponse(
                    call: Call<MartAllLogInResponse>,
                    response: Response<MartAllLogInResponse>
                ) {
                    Log.d("[LOGIN]", response.toString())
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null && body.results != null) {
                            GlobalScope.launch {
                                userInfoManager.updateTokens(
                                    accessToken = body!!.results.accessToken,
                                    refreshToken = body.results.refreshToken,
                                    accessTokenExpire = body.results.accessTokenExpiredDate,
                                    refreshTokenExpire = body.results.refreshTokenExpiredDate
                                )
                                Log.d("[PRINT]", "${userInfoManager.getToken()}")
                                val intent =
                                    Intent(this@MartAllLoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Log.d("[LOGIN]", "로그인 실패: 응답 본문이 비어 있거나 result가 null입니다.")
                        }
                    } else {
                        Log.d("[LOGIN]", "로그인 실패: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<MartAllLogInResponse>, t: Throwable) {
                    Log.d("[LOGIN]", "통신 실패: ${t.message}")
                }
            })
        }
    }
}