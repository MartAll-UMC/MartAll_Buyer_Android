package com.org.martall.interfaces

import com.org.martall.models.ItemLikedResponseDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemApiInterface {
    @POST("/item-itemLike/{itemId}")
    fun likedItem(
        @Path("itemId") itemId: Int)
    : Call<ItemLikedResponseDTO>

    @DELETE("/item-itemLike/{itemId}")
    fun unLikedItem(
        @Path("itemId") itemId: Int)
    : Call<ItemLikedResponseDTO>
}