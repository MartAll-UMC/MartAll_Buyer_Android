package com.org.martall.interfaces

import com.org.martall.Model.NoticeOrderDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NoticeOrderApi {
    @GET("/user/order")
    fun getOrderStatus(): Call<NoticeOrderDTO>

}
