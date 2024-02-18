package com.org.martall.interfaces

import com.org.martall.models.DibsProductResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DibsProductApi {
    @GET("/item-like")
    fun getDibsProduct(): Call<DibsProductResponseDTO>

    @GET("item-like/{itemId}")
    fun cancelDibsProduct(@Path("itemId") itemId: Any): Call<DibsProductResponseDTO>
}
