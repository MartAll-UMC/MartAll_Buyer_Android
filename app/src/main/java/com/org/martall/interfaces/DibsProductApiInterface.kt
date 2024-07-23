package com.org.martall.interfaces

import com.org.martall.models.LikeItemResponseDTO
import com.org.martall.models.ItemLikedResponseDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DibsProductApiInterface {
    @GET("/item-itemLike")
    fun getDibsProduct(): Call<LikeItemResponseDTO>

    @POST("/item-itemLike/{itemId}")
    fun likedItem(
        @Path("itemId") itemId: Int)
    : Call<ItemLikedResponseDTO>

    @DELETE("/item-itemLike/{itemId}")
    fun unLikedItem(
        @Path("itemId") itemId: Int)
    : Call<ItemLikedResponseDTO>
}
