package com.org.martall.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.org.martall.BuildConfig
import com.org.martall.model.MartSimpleDTO
import com.org.martall.model.SearchResponse
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/mart/shops/search/keyword")
    fun searchMartList(
        @Query("keyword") keyword: String,
    ): Call<SearchResponse>

    @GET("/item/search")
    fun searchItemList(
        @Query("itemName") keyword: String,
    ): Call<SearchResponse>

    companion object {
        private const val MOCK_MART = BuildConfig.MOCK_MART_URL
        private const val MOCK_ITEM = BuildConfig.MOCK_ITEM_URL
        private val gson: Gson = GsonBuilder().setLenient().create()

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
}