package com.org.martall.services

import com.org.martall.BuildConfig
import com.org.martall.interfaces.MartApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceManager {
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

    val MartapiService: MartApiService = retrofit.create(MartApiService::class.java)
}