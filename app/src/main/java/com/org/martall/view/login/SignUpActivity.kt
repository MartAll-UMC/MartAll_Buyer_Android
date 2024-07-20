package com.org.martall.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk.keyHash
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.databinding.ActivitySignUpBinding
import com.org.martall.models.SignUpIdCheckResponse
import com.org.martall.models.SignUpRequest
import com.org.martall.models.SignUpResponse
import com.org.martall.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private lateinit var api: ApiService
    private var name: String = ""
    private var id: String = ""
    private var password: String = ""
    private var passwordCheck: String = ""
    private var email:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backIv.setOnClickListener {
            finish()
        }

        // 필드 텍스트 변경 리스너 설정
        setupTextWatchers()

        // 아이디 중복확인 버튼 클릭 시
        binding.idCheckBtn.setOnClickListener {
            idDupCheck()
        }

        // 회원가입 버튼 클릭 시
        binding.signupBtn.setOnClickListener {
            validateFields()
        }
    }

    private fun setupTextWatchers() {
        binding.nameEt.addTextChangedListener(createTextWatcher { validateName() })
        binding.idEt.addTextChangedListener(createTextWatcher { validateId() })
        binding.passwordEt.addTextChangedListener(createTextWatcher { validatePassword() })
        binding.passwordCheckEt.addTextChangedListener(createTextWatcher { validatePasswordCheck() })
        binding.emailEt.addTextChangedListener(createTextWatcher { validateEmail() })
    }

    private fun createTextWatcher(validationFunction: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validationFunction()
                updateSignupButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun validateFields() {
        validateName()
        validateId()
        validatePassword()
        validatePasswordCheck()
        validateEmail()
        updateSignupButtonState()

        if (isFormValid()) {
            signUp()
        } else {
            binding.guideTv.text = "입력을 완료해주세요"
        }
    }

    private fun validateName() {
        name = binding.nameEt.text.toString().trim()
        if (TextUtils.isEmpty(name)) {
            binding.nameEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            binding.nameErrorTv.visibility = View.VISIBLE
        } else {
            binding.nameEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            binding.nameErrorTv.visibility = View.GONE
        }
    }

    private fun validateId() {
        id = binding.idEt.text.toString().trim()
        if (TextUtils.isEmpty(id)) {
            binding.idEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            binding.idErrorTv.visibility = View.VISIBLE
        } else {
            binding.idEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            binding.idErrorTv.visibility = View.GONE
        }
    }

    private fun validatePassword() {
        password = binding.passwordEt.text.toString().trim()

        if (TextUtils.isEmpty(password)) {
            binding.passwordEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            binding.passwordErrorTv.visibility = View.VISIBLE
            binding.passwordGuideTv.visibility = View.GONE
        } else if (!TextUtils.isEmpty(password) && !PASSWORD_PATTERN.matcher(password).matches()) {
            binding.passwordEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            binding.passwordGuideTv.setTextColor(ContextCompat.getColor(this, R.color.pink))
            binding.passwordErrorTv.visibility = View.GONE
            binding.passwordGuideTv.visibility = View.VISIBLE
        } else {
            binding.passwordEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            binding.passwordErrorTv.visibility = View.GONE
            binding.passwordGuideTv.visibility = View.VISIBLE
            binding.passwordGuideTv.setTextColor(ContextCompat.getColor(this, R.color.grey700))
        }
    }

    private fun validatePasswordCheck() {
        passwordCheck = binding.passwordCheckEt.text.toString().trim()
        if (TextUtils.isEmpty(passwordCheck)) {
            binding.passwordCheckEt.setBackgroundResource(R.drawable.bg_red_square_r8)
        }
        else if (!TextUtils.isEmpty(password) && password != passwordCheck) {
            binding.passwordCheckEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            binding.passwordGuideTv.text = "비밀번호가 다릅니다."
            binding.passwordGuideTv.visibility = View.VISIBLE
            binding.passwordGuideTv.setTextColor(ContextCompat.getColor(this, R.color.pink))
        } else {
            binding.passwordCheckEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            binding.passwordGuideTv.visibility = View.GONE
        }
    }

    private fun validateEmail() {
        email = binding.emailEt.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            binding.emailEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            binding.emailGuideTv.visibility = View.GONE
            binding.emailAlertTv.visibility = View.VISIBLE
            binding.emailErrorTv.visibility = View.GONE

        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setBackgroundResource(R.drawable.bg_red_square_r8)
            binding.emailGuideTv.visibility = View.GONE
            binding.emailAlertTv.visibility = View.GONE
            binding.emailErrorTv.visibility = View.VISIBLE

        } else {
            binding.emailEt.setBackgroundResource(R.drawable.background_gray_square_r8)
            binding.emailGuideTv.visibility = View.VISIBLE
            binding.emailErrorTv.visibility = View.GONE
            binding.emailAlertTv.visibility = View.GONE
        }
    }

    private fun updateSignupButtonState() {
        val isNameValid = !TextUtils.isEmpty(name)
        val isIdValid = !TextUtils.isEmpty(id)
        val isPasswordValid = !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches()
        val isPasswordCheckValid = password == passwordCheck
        val isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (isNameValid && isIdValid && isPasswordValid && isPasswordCheckValid && isEmailValid) {
            binding.signupBtn.setBackgroundResource(R.drawable.bg_blue_border8)
        } else {
            binding.signupBtn.setBackgroundResource(R.drawable.bg_grey300_border_r8)
        }
    }

    private fun isFormValid(): Boolean {
        val isNameValid = !TextUtils.isEmpty(name)
        val isIdValid = !TextUtils.isEmpty(id)
        val isPasswordValid =
            !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches()
        val isPasswordCheckValid = password == passwordCheck
        val isEmailValid =
            !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        return isNameValid && isIdValid && isPasswordValid && isPasswordCheckValid && isEmailValid
    }

    private fun idDupCheck() {

        // 아이디 중복 서버 통신
        if (!TextUtils.isEmpty(name)) {
            Log.d("[SIGNUP]", "아이디 중복 요청: $id")

            val apiService = ApiService.createMock()

            apiService.idDupCheck(id).enqueue(object: Callback<SignUpIdCheckResponse> {
                override fun onResponse(
                    call: Call<SignUpIdCheckResponse>,
                    response: Response<SignUpIdCheckResponse>
                ) {
                    if (response.isSuccessful) {
                        val signUpIdCheckResponse = response.body()
                        if (signUpIdCheckResponse != null && signUpIdCheckResponse.result.idDupCheck) {
                            // 아이디 중복확인 성공
                            Log.d("[SIGNUP]", "아이디 중복 확인 성공")

                            binding.idCheckBtn.setBackgroundResource(R.drawable.bg_primary300_r8)
                            binding.idCheckBtn.text = "중복 확인 완료"
                            binding.idCheckBtn.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.white))
                        }
                        else {
                            Log.d("[SIGNUP]", "아이디 중복")
                            Toast.makeText(this@SignUpActivity, "아이디가 중복되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("[SIGNUP]", "아이디 중복 확인 실패")
                    }
                }

                override fun onFailure(call: Call<SignUpIdCheckResponse>, t: Throwable) {
                    Log.d("[SIGNUP]", "아이디 중복 확인 - 통신 실패")
                }
            })
        } else {
            Log.d("[SIGNUP]", "아이디가 비어 있습니다.")
        }
    }

    // 회원가입 통신
    private fun signUp() {
        val request = SignUpRequest(name, id, password, email)
        Log.d("[SIGNUP]", "회원가입 요청: $request")

        val apiService = ApiService.createMock()

        // 서버 -> 회원가입 요청
        apiService.signUp(request).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    val signUpResponse = response.body()
                    // 응답 처리
                    if (signUpResponse != null) {
                        // 회원가입 성공
                        Log.d("[SIGNUP]", "회원가입 성공")
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                    }
                } else {
                    Log.d("[SIGNUP]", "회원가입 실패")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("[SIGNUP]", "통신 실패")
            }
        })
    }

    companion object {
        private val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
        )
    }
}