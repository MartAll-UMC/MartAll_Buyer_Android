package com.org.martall.interfaces

import com.org.martall.model.FollowResponseDTO
import com.org.martall.model.MartDetailDTO
import com.org.martall.model.MartDetailResponseDTO
import com.org.martall.model.MartListResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MartApiService {
    @GET("/mart/shops/all")
    fun getAllShops(): Call<MartListResponseDTO>

    @GET("/mart/shops/{shopid}/detail")
    fun getMartDetail(
        @Path("shopid") shopId: Int)
    : Call<MartDetailResponseDTO>

    @GET("/mart/shops/{shopid}/follow")
    fun followMart(
        @Path("shopid") shopId: Int)
    : Call<FollowResponseDTO>

    @GET("/mart/shops/{shopid}/unfollow")
    fun unfollowMart(
        @Path("shopid") shopId: Int)
            : Call<FollowResponseDTO>
}