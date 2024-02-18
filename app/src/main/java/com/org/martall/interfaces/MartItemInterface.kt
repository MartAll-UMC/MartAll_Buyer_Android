package com.org.martall.interfaces

import com.org.martall.models.Response
import com.org.martall.models.ResponseMart
import com.org.martall.models.SecondResponse
import retrofit2.Call
import retrofit2.http.*

interface MartItemService {
    @GET("/item/new-item")
    fun getNewItem(): Call<Response>
}

interface MartRecommendService {
    @GET("/mart/shops/recommended")
    fun getRecommendMart(): Call<ResponseMart>
}

interface MartItemdibs {
    @POST("/item-like/{itemId}")
    fun dibsItem(@Path("itemId") itemId: Int, @Query("like") like: Boolean): Call<Unit>

    @DELETE("/item-like/{itemId}")
    fun cancelDibsItem(@Path("itemId") itemId: Int): Call<Unit>
}

interface CategoryService {
    @GET("/item/category")
    fun getCategoryItem(
        @Query("category") category: String,
        @Query("minPrice") minPrice: Int,
        @Query("maxPrice") maxPrice: Int,
        @Query("sort") sort: String
    ): Call<SecondResponse>
}