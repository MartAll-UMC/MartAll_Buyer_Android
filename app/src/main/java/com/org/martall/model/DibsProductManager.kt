package com.org.martall.Model

import com.org.martall.BuildConfig
import com.org.martall.interfaces.DibsProductApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DibsProductManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MOCK_CART_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    val dibsProductApiService: DibsProductApi by lazy {
        retrofit.create(DibsProductApi::class.java)
    }
}