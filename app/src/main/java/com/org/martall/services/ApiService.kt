package com.org.martall.services

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.org.martall.BuildConfig
import com.org.martall.BuildConfig.BASE_URL
import com.org.martall.interfaces.CartApiInterface
import com.org.martall.interfaces.CategoryInterface
import com.org.martall.interfaces.DibsMartApiInterface
import com.org.martall.interfaces.DibsProductApiInterface
import com.org.martall.interfaces.HomeInterface
import com.org.martall.interfaces.MartApiInterface
import com.org.martall.interfaces.OrderApiInterface
import com.org.martall.models.LoginRequest
import com.org.martall.models.LoginResponse
import com.org.martall.models.MartAllLogInRequest
import com.org.martall.models.MartAllLogInResponse
import com.org.martall.models.RefreshResponse
import com.org.martall.models.SearchItemResponse
import com.org.martall.models.SearchMartResponse
import com.org.martall.models.SignUpIdCheckResponse
import com.org.martall.models.SignUpRequest
import com.org.martall.models.SignUpResponse
import com.org.martall.models.UserResponseDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.IOException

interface ApiService : HomeInterface, MartApiInterface, CartApiInterface, CategoryInterface,
    DibsMartApiInterface, DibsProductApiInterface, OrderApiInterface {
    @GET("/mart/shops/search")
    fun searchMartList(
        @Query("keyword") keyword: String,
    ): Call<SearchMartResponse>

    @GET("/item/search")
    fun searchItemList(
        @Query("itemName") keyword: String,
    ): Call<SearchItemResponse>

    @POST("/user/login-kakao")
    fun login(
        @Body body: LoginRequest,
    ): Call<LoginResponse>

    @GET("/user/refresh")
    fun refreshToken(
        @Header("refresh-token") refreshToken: String,
    ): Call<RefreshResponse>

    @DELETE("/user/delete")
    fun withdraw(): Call<Unit>

    @GET("/user/profile")
    fun getUserProfile(): Call<UserResponseDTO>

    @POST("/user/join")
    fun signUp(@Body request: SignUpRequest) : Call<SignUpResponse>

    @GET("/user/join/idDupcheck")
    fun idDupCheck(@Query("id") id: String) : Call<SignUpIdCheckResponse>

    @POST("/user/login")
    fun logIn(@Body request: MartAllLogInRequest) : Call<MartAllLogInResponse>


    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
        private const val MOCK_URL = BuildConfig.MOCK_URL
        private val gson: Gson = GsonBuilder().setLenient().create()
        private lateinit var userInfoManager: UserInfoManager
        private lateinit var MartAllInfoManager: MartAllUserInfoManager

        fun create(): ApiService {
            return retrofit2.Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        fun createMock(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder().build()
                    val response = chain.proceed(request)
                    var responseBodyString = response.body?.string() ?: "null"

                    // 선행 공백 제거
                    responseBodyString = responseBodyString.trim()

                    Log.d("[PRINT/interceptor]", "Request URL: ${request.url}")
                    Log.d("[PRINT/interceptor]", "Response code: ${response.code}")
                    Log.d("[PRINT/interceptor]", "Response body: $responseBodyString")

                    response.newBuilder()
                        .body(ResponseBody.create(response.body?.contentType(), responseBodyString))
                        .build()
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(MOCK_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        suspend fun createWithHeader(context: Context): ApiService {
            userInfoManager = UserInfoManager(context)
            val interceptor =
                AppInterceptor(userInfoManager.getTokens(), userInfoManager.needRefreshToken())
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return retrofit2.Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        suspend fun createWithMartAllHeader(context: Context): ApiService {
            val userInfoManager = MartAllUserInfoManager(context)
            val tokens = withContext(Dispatchers.IO) {
                userInfoManager.getToken()
            }
            val needRefresh = withContext(Dispatchers.IO) {
                userInfoManager.needRefreshToken()
            }
            val interceptor = MartAllAppInterceptor(tokens, needRefresh)
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            Log.d("[API]", "Creating Retrofit instance")
            return Retrofit.Builder()
                .baseUrl(MOCK_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java).also {
                    Log.d("[API]", "Retrofit instance created: $it")
                }
        }
    }

    class AppInterceptor(
        private val tokens: Pair<String?, String?>,
        private val needRefresh: Boolean,
    ) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response = with(chain) {
            if (needRefresh) {
                create().refreshToken(tokens.second ?: "")
                    .enqueue(object : retrofit2.Callback<RefreshResponse> {
                        override fun onResponse(
                            call: Call<RefreshResponse>,
                            response: retrofit2.Response<RefreshResponse>,
                        ) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                GlobalScope.launch {
                                    userInfoManager.updateAccessToken(
                                        body?.result?.accessToken ?: "",
                                        body?.result?.accessTokenExpiredDate ?: ""
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<RefreshResponse>, t: Throwable) {
                            t.printStackTrace()
                            Log.e("[ERROR/interceptor]", "재발급 실패")
                        }
                    })
            }

            val newRequest = request().newBuilder()
                .addHeader("access-token", "${tokens.first ?: ""}")
                .addHeader("refresh-token", "${tokens.second ?: ""}")
                .build()

            val response = chain.proceed(newRequest)

            Log.d("[PRINT/interceptor]", newRequest.url.toString())
            Log.d("[PRINT/interceptor]", response.body?.string() ?: "null")

            return chain.proceed(newRequest)
        }
    }


    class MartAllAppInterceptor(
        private val tokens: Pair<String?, String?>,
        private val needRefresh: Boolean
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response = with(chain) {
            Log.d("[PRINT/interceptor]", "Intercepting request")

            if (needRefresh) {
                Log.d("[PRINT/interceptor]", "Need to refresh token")
                val response = ApiService.createMock().refreshToken(tokens.second ?: "").execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        GlobalScope.launch(Dispatchers.IO) {
                            userInfoManager.updateAccessToken(body.result.accessToken, body.result.accessTokenExpiredDate)
                        }
                    }
                }
            }

            val newRequest = request().newBuilder()
                .addHeader("access-token", tokens.first ?: "")
                .addHeader("refresh-token", tokens.second ?: "")
                .build()

            Log.d("[PRINT/interceptor]", "New request URL: ${newRequest.url}")

            val response = chain.proceed(newRequest)

            Log.d("[PRINT/interceptor]", "Response code: ${response.code}")
            val responseBodyString = response.body?.string() ?: "null"
            Log.d("[PRINT/interceptor]", responseBodyString)

            return response.newBuilder()
                .body(ResponseBody.create(response.body?.contentType(), responseBodyString))
                .build()
        }
    }
}