package com.org.martall.interfaces

import com.org.martall.models.NoticeOrderDTO
import retrofit2.Call
import retrofit2.http.GET

interface NoticeOrderApi {
    @GET("/user/order")
    fun getOrderStatus(): Call<NoticeOrderDTO>

}
