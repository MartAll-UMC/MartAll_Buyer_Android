package com.org.martall.services

import com.org.martall.BuildConfig
import com.org.martall.interfaces.ItemApiService
import com.org.martall.interfaces.MartApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ItemApiServiceManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MOCK_ITEM_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    val ItemapiService: ItemApiService = retrofit.create(ItemApiService::class.java)
}