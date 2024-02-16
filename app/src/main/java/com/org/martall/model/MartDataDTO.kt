package com.org.martall.model

import com.google.gson.annotations.SerializedName

data class MartDataDTO(
    @SerializedName("martId") val martId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("followersCount") val followersCount: Int,
    @SerializedName("visitorsCount") val visitorsCount: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("items") val items: List<MartItemDTO>
)

class MartItemDTO(
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("price") val price: Int,
    @SerializedName("favoriteLink") val favoriteLink: String,
    @SerializedName("detailLink") val detailLink: String
)
