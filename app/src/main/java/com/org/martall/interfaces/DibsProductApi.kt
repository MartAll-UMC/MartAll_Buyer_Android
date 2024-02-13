package com.org.martall.interfaces

import com.org.martall.model.DibsProductResponseDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DibsProductApi {
    @GET("/item-like")
    fun getDibsProduct(): Call<DibsProductResponseDTO>

    @DELETE("item-like/{itemId}")
    fun cancelDibsProduct(@Path("itemId") itemId: Int): Call<DibsProductResponseDTO>
}
