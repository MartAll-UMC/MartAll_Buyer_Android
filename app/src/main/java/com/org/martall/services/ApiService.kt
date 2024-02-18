package com.org.martall.services

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.org.martall.BuildConfig
import com.org.martall.interfaces.CartApiInterface
import com.org.martall.interfaces.DibsMartApiInterface
import com.org.martall.models.LoginRequest
import com.org.martall.models.LoginResponse
import com.org.martall.models.RefreshResponse
import com.org.martall.models.SearchResponse
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

interface ApiService {
    @GET("/mart/shops/search/keyword")
    fun searchMartList(
        @Query("keyword") keyword: String,
    ): Call<SearchResponse>

    @GET("/item/search")
    fun searchItemList(
        @Query("itemName") keyword: String,
    ): Call<SearchResponse>

    @POST("/user/login-kakao")
    fun login(
        @Body body: LoginRequest,
    ): Call<LoginResponse>

    @GET("/user/refresh")
    fun refreshToken(
        @Header("refresh-token") refreshToken: String,
    ): Call<RefreshResponse>

    companion object {
        private const val MOCK_MART = BuildConfig.MOCK_MART_URL
        private const val MOCK_ITEM = BuildConfig.MOCK_ITEM_URL
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
            return retrofit2.Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        fun createMartVer(): ApiService {
            return retrofit2.Retrofit.Builder().baseUrl(MOCK_MART)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        fun createItemVer(): ApiService {
            return retrofit2.Retrofit.Builder().baseUrl(MOCK_ITEM)
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
//            val headers = response.headers()
//            val body = response.body()?.string()

            return response
        }
    }
}