package com.org.martall.interfaces

import com.org.martall.model.DibsProductResponseDTO
import retrofit2.Call
import retrofit2.http.GET

interface DibsProductApi {
    @GET("/item-like")
    fun getDibsProduct(): Call<DibsProductResponseDTO>
}
