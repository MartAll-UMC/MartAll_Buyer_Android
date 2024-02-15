package com.org.martall.interfaces

import com.org.martall.Model.DibsMartResponseDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DibsMartApi {
    @GET("/mart/shops/follows")
    fun getDibsMart(): Call<DibsMartResponseDTO>

    @GET("/mart/shops/{shopid}/unfollow")
    fun cancelDibsMart(@Path("shopid") shopid: Int): Call<DibsMartResponseDTO>


}
