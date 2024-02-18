package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class MartLikedResponseDTO(
    @SerializedName("timeStamp") val timeStamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result,
    @SerializedName("success") val success: Boolean
)

data class Result(
    @SerializedName("item") val items: List<LikedItemListDTO>
)

data class LikedItemListDTO(
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("picName") val picName: String,
    @SerializedName("itemName") val itemName: String,
    @SerializedName("price") val price: Int,
    @SerializedName("martShopId") val martShopId: Long,
    @SerializedName("martName") val martName: String
)
