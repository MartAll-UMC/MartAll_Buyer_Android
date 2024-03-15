package com.org.martall.interfaces

import com.org.martall.models.SecondResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryInterface {
    @GET("/item/category")
    fun getCategoryItem(
        @Query("category") category: String,
        @Query("minPrice") minPrice: Int,
        @Query("maxPrice") maxPrice: Int,
        @Query("sort") sort: String,
    ): Call<SecondResponse>
}