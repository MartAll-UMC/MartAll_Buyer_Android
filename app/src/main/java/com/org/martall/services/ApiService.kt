package com.org.martall.services

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.org.martall.BuildConfig
import com.org.martall.interfaces.CartApiInterface
import com.org.martall.interfaces.CategoryInterface
import com.org.martall.interfaces.HomeInterface
import com.org.martall.interfaces.MartApiInterface
import com.org.martall.models.LoginRequest
import com.org.martall.models.LoginResponse
import com.org.martall.models.RefreshResponse
import com.org.martall.models.SearchItemResponse
import com.org.martall.models.SearchMartResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.IOException

interface ApiService : HomeInterface, MartApiInterface, CartApiInterface, CategoryInterface {
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

    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
        private val gson: Gson = GsonBuilder().setLenient().create()
        private lateinit var userInfoManager: UserInfoManager

        fun create(): ApiService {
            return retrofit2.Retrofit.Builder().baseUrl(BASE_URL)
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
}