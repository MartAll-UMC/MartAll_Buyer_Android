package com.org.martall.interfaces

import com.org.martall.models.Response
import com.org.martall.models.ResponseMart
import com.org.martall.models.SecondResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeInterface {
    @GET("/item/new-item")
    fun getNewItem(): Call<Response>

    @GET("/mart/shops/recommended")
    fun getRecommendMart(): Call<ResponseMart>
}
//interface MartItemdibs {
//    @POST("/item-like/{itemId}")
//    fun dibsItem(@Path("itemId") itemId: Int, @Query("like") like: Boolean): Call<Unit>
//
//    @DELETE("/item-like/{itemId}")
//    fun cancelDibsItem(@Path("itemId") itemId: Int): Call<Unit>
//}