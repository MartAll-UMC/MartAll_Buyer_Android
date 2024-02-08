package com.org.martall.interfaces

import com.org.martall.model.DibsMartResponseDTO
import retrofit2.Call
import retrofit2.http.GET

interface DibsMartApi {
    @GET("/mart/shops/follows")
    fun getDibsMart(): Call<DibsMartResponseDTO>

}
