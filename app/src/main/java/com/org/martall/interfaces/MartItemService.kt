package com.org.martall.interfaces

import com.org.martall.models.Response
import com.org.martall.models.ResponseMart
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface MartItemService {
    @GET("item/new-item")
    fun getNewItem(): Call<Response>
}

interface MartRecommendService {
    @GET("mart/shops/recommended")
    fun getRecommendMart(): Call<ResponseMart>
}

interface MartItemdibs {
    @POST("item-like/{itemId}")
    fun dibsItem(@Path("itemId") itemId: Int, @Query("isLiked") isLiked: Boolean): Call<Unit>

    @DELETE("item-like/{itemId}")
    fun cancelDibsItem(@Path("itemId") itemId: Int): Call<Unit>
}


