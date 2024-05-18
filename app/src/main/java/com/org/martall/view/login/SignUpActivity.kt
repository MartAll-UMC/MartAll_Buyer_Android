package com.org.martall.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.org.martall.R
import com.org.martall.databinding.ActivitySignUpBinding
import com.org.martall.services.ApiService

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private lateinit var api: ApiService

    private var name: String = ""
    private var id: String = ""
    private var passWord: String = ""
    private var email:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        name = binding.nameEt.text.toString()
        id = binding.idEt.text.toString()
        passWord = binding.passwordEt.text.toString()
        email = binding.emailEt.text.toString()

        binding.idCheckBtn.setOnClickListener {
            

        }

        /*
        binding.kakaoLoginBtn.setOnClickListener {
            var keyHash = Utility.getKeyHash(this)
            Log.d("[LOGIN]", "keyHash: $keyHash")

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("[LOGIN]", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("[LOGIN]", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    login()
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
                        login()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    this,
                    callback = callback
                )
            }
        }
         */
    }
}