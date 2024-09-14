package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class MartDataDTO(
    @SerializedName("martId") val martId: Int,
    @SerializedName("martImg") val imageUrl: String,
    @SerializedName("martName") val name: String,
    @SerializedName("martCategory") val categories: List<String>,
    @SerializedName("bookmarkCount") val followersCount: Int,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("martBookmark") var bookmarkYn: Boolean,
    @SerializedName("items") val items: List<MartItemDTO>,
)

class MartItemDTO(
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("itemImg") val imageUrl: String,
    @SerializedName("itemName") val name: String,
    @SerializedName("itemPrice") val price: Int,
    @SerializedName("itemLike") var likeYn: Boolean,
)
