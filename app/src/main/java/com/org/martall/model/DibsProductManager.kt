package com.org.martall.model

import com.org.martall.BuildConfig
import com.org.martall.interfaces.DibsProductApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DibsProductManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MOCK_ITEM_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dibsProductApiService: DibsProductApi by lazy {
        retrofit.create(DibsProductApi::class.java)
    }
}