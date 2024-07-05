package com.org.martall.view.login

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.org.martall.BuildConfig
import com.org.martall.MainActivity
import com.org.martall.databinding.ActivityLoginBinding
import com.org.martall.models.LoginRequest
import com.org.martall.models.LoginResponse
import com.org.martall.services.ApiService
import com.org.martall.services.UserInfoManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var api: ApiService
    private lateinit var userInfoManager: UserInfoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.contatctTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        api = ApiService.create()
        userInfoManager = UserInfoManager(applicationContext)

        binding.kakaoLoginBtn.setOnClickListener {
            var keyHash = Utility.getKeyHash(this)
            Log.d("[LOGIN]", "keyHash: $keyHash")

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("[LOGIN]", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("[LOGIN]", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    kakaoLogin()
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(applicationContext)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("[LOGIN]", "카카오톡으로 로그인 실패", error)

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i("[LOGIN]", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        kakaoLogin()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    this,
                    callback = callback
                )
            }
        }

        binding.martAllLoginBtn.setOnClickListener {
            Log.d("[LOGIN]", "마트올로 로그인 버튼 클릭")
            val intent = Intent(this@LoginActivity, MartAllLoginActivity::class.java)
            startActivity(intent)
        }

        binding.contatctTv.setOnClickListener {
            val url = BuildConfig.KAKAO_CHANNEL_URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun kakaoLogin() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("[LOGIN]", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                api.kakaoLogin(
                    LoginRequest(
                        providerId = user.id.toString(),
                        email = user.kakaoAccount?.email.toString(),
                        name = user.kakaoAccount?.profile?.nickname.toString(),
                        img = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                    )
                ).enqueue(object : retrofit2.Callback<LoginResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<LoginResponse>,
                        response: retrofit2.Response<LoginResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()

                            GlobalScope.launch {
                                userInfoManager.updateTokens(
                                    accessToken = body!!.results.accessToken,
                                    refreshToken = body.results.refreshToken,
                                    accessTokenExpire = body.results.accessTokenExpiredDate,
                                    refreshTokenExpire = body.results.refreshTokenExpiredDate
                                )

                                Log.d("[PRINT]", "${userInfoManager.getTokens()}")
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "로그인에 실패했습니다", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<LoginResponse>,
                        t: Throwable,
                    ) {
                        Log.e("[ERROR]", "${t.message}")
                        Toast.makeText(this@LoginActivity, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}