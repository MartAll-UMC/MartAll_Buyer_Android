package com.org.martall.models

import com.org.martall.BuildConfig
import com.org.martall.interfaces.NoticeOrderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NoticeOrderManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MOCK_CART_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val NoticeOrderApiService: NoticeOrderApi by lazy {
        retrofit.create(NoticeOrderApi::class.java)
    }
}