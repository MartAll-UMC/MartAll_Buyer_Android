package com.org.martall.interfaces

import com.org.martall.models.NoticeOrderDTO
import com.org.martall.models.OrderRequestDTO
import com.org.martall.models.OrderResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderApiInterface {
    @GET("/user/order")
    fun getOrderStatus(): Call<NoticeOrderDTO>

    @POST("/user/order")
    fun orderProduct(
        @Body body: OrderRequestDTO,
    ): Call<OrderResponseDTO>
}
