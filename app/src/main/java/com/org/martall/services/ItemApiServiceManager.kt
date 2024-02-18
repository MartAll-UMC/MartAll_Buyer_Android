package com.org.martall.services

import com.org.martall.BuildConfig
import com.org.martall.interfaces.ItemApiInterface
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

    val ItemapiService: ItemApiInterface = retrofit.create(ItemApiInterface::class.java)
}