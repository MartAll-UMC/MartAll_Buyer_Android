package com.org.martall.interfaces

import com.org.martall.models.MartListResponseDTO
import com.org.martall.models.ProductDetailResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApiInterface {
    @GET("/item/{shopid}/{itemid}")
    fun getProductDetail(
        @Path("shopid") shopId: Int, @Path ("itemid") itemId: Int)
            : Call<ProductDetailResponseDTO>

    @GET("/mart/shops/search/filter")
    fun ShowAllShops(
        @Query("tag") tag: String?,
        @Query("minBookmark") minBookmark: Int?,
        @Query("maxBookmark") maxBookmark: Int?,
        @Query("minLike") minLike: Int?,
        @Query("maxLike") maxLike: Int?,
        @Query("sort") sort: String?
    ): Call<MartListResponseDTO>
}