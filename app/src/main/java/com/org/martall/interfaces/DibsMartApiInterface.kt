package com.org.martall.interfaces

import com.org.martall.models.BookMarkResponseDTO
import com.org.martall.models.FollowResponseDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DibsMartApiInterface {
    @GET("/mart/shops/follows")
    fun getDibsMart(): Call<BookMarkResponseDTO>

    @GET("/mart/shops/{shopid}/unfollow")
    fun unfollowMart(
        @Path("shopid") shopId: Int
    ): Call<FollowResponseDTO>
}