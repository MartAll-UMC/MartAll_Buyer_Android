package com.org.martall.model

import com.org.martall.BuildConfig
import com.org.martall.interfaces.DibsMartApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DibsMartManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MOCK_MART_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dibsMartApiService: DibsMartApi by lazy {
        retrofit.create(DibsMartApi::class.java)
    }
}