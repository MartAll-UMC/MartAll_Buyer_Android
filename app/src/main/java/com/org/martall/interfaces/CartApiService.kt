package com.org.martall.interfaces

import com.org.martall.model.MartDetailResponseDTO
import com.org.martall.model.ProductDetailResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CartApiService {
    @GET("/item/{shopid}/{itemid}")
    fun getProductDetail(
        @Path("shopid") shopId: Int, @Path ("itemid") itemId: Int)
            : Call<ProductDetailResponseDTO>
}