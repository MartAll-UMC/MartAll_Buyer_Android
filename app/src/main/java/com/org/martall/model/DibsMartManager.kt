package com.org.martall.Model

import com.org.martall.BuildConfig
import com.org.martall.interfaces.DibsMartApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DibsMartManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MOCK_MART_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    val dibsMartApiService: DibsMartApi by lazy {
        retrofit.create(DibsMartApi::class.java)
    }
}