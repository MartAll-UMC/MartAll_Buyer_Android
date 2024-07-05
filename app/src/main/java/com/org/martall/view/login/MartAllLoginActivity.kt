package com.org.martall.view.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.databinding.ActivityMartAllLoginBinding
import com.org.martall.models.LoginResponse
import com.org.martall.models.MartAllLogInRequest
import com.org.martall.services.ApiService
import com.org.martall.services.UserInfoManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartAllLoginActivity : AppCompatActivity() {
    private var id: String = ""
    private var password: String = ""

    private lateinit var api: ApiService
    private lateinit var userInfoManager: UserInfoManager
    private val binding by lazy { ActivityMartAllLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        api = ApiService.createMock()
        userInfoManager = UserInfoManager(applicationContext)


        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.signupTv.setOnClickListener {
            val intent = Intent(this@MartAllLoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun login() {
        id = binding.loginIdEt.text.toString().trim()
        password = binding.loginPasswordEt.text.toString().trim()

        // id 검증
        val isIdValid: Boolean = if (TextUtils.isEmpty(id)) {
            binding.loginIdEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            false
        } else {
            binding.loginIdEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            true
        }

        // 비밀번호 검증
        val isPasswordValid: Boolean = if (TextUtils.isEmpty(password)) {
            binding.loginPasswordEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            false
        } else {
            binding.loginPasswordEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            true
        }

        if (isIdValid && isPasswordValid) {
            api.login(
                MartAllLogInRequest(
                    id = id,
                    password = password
                )
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            val responseString = body.string().replace(Regex("[^\\x20-\\x7E]"), "")
                            val gson = Gson()
                            val loginResponse =
                                gson.fromJson(responseString, LoginResponse::class.java)

                            GlobalScope.launch {
                                userInfoManager.updateTokens(
                                    accessToken = loginResponse.results.accessToken,
                                    refreshToken = loginResponse.results.refreshToken,
                                    accessTokenExpire = loginResponse.results.accessTokenExpiredDate,
                                    refreshTokenExpire = loginResponse.results.refreshTokenExpiredDate
                                )

                                Log.d("[PRINT]", "${userInfoManager.getTokens()}")
                                val intent =
                                    Intent(this@MartAllLoginActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        } else {
                            Log.d("[LOGIN]", "로그인 실패: ${response.errorBody()?.string()}")
                            Toast.makeText(
                                this@MartAllLoginActivity,
                                "로그인에 실패했습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("[LOGIN]", "통신 실패: $t")
                    Toast.makeText(
                        this@MartAllLoginActivity,
                        "로그인에 실패했습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}