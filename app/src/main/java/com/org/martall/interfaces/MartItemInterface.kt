package com.org.martall.interfaces

import com.org.martall.models.Response
import com.org.martall.models.ResponseMart
import com.org.martall.models.SecondResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeInterface {
    @GET("/item/new-item")
    fun getNewItem(): Call<Response>

    @GET("/mart/today")
    fun getRecommendMart(): Call<ResponseMart>
}
//interface MartItemdibs {
//    @POST("/item-itemLike/{itemId}")
//    fun dibsItem(@Path("itemId") itemId: Int, @Query("itemLike") itemLike: Boolean): Call<Unit>
//
//    @DELETE("/item-itemLike/{itemId}")
//    fun cancelDibsItem(@Path("itemId") itemId: Int): Call<Unit>
//}