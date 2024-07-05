package com.org.martall.services

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.org.martall.BuildConfig
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
import com.org.martall.models.RefreshResponse
import com.org.martall.models.SearchItemResponse
import com.org.martall.models.SearchMartResponse
import com.org.martall.models.SignUpIdCheckResponse
import com.org.martall.models.SignUpRequest
import com.org.martall.models.SignUpResponse
import com.org.martall.models.UserResponseDTO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
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
    fun kakaoLogin(
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
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @GET("/user/join/idDupcheck")
    fun idDupCheck(@Query("id") id: String): Call<SignUpIdCheckResponse>

    @POST("/user/login")
    fun login(@Body body: MartAllLogInRequest): Call<ResponseBody>

    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
        private const val MOCK_URL = BuildConfig.MOCK_URL
        private val gson: Gson = GsonBuilder().setLenient().create()
        private lateinit var userInfoManager: UserInfoManager

        fun create(): ApiService {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        fun createMock(): ApiService {
            return Retrofit.Builder().baseUrl(MOCK_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        suspend fun createWithHeader(context: Context): ApiService {
            userInfoManager = UserInfoManager(context)
            val interceptor =
                AppInterceptor(userInfoManager.getTokens(), userInfoManager.needRefreshToken())
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
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
                .addHeader("access-token", tokens.first ?: "")
                .addHeader("refresh-token", tokens.second ?: "")
                .build()

            val response = chain.proceed(newRequest)

            Log.d("[PRINT/interceptor]", newRequest.url.toString())
            Log.d("[PRINT/interceptor]", response.body?.string() ?: "null")

            return chain.proceed(newRequest)
        }
    }
}