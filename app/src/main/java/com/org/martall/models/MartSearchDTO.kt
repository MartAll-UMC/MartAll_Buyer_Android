package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class SearchMartResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val marts: List<MartSimpleDTO>?,
)

data class SearchItemResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val items: List<ItemSearchDTO>?,
)

data class MartSimpleDTO(
    @SerializedName("name") val name: String,
    @SerializedName("martcategory") val categories: List<String>,
    @SerializedName("followersCount") val followerCnt: Int,
    @SerializedName("visitorsCount") val likeCnt: Int,
    @SerializedName("isFavorite") val isFollowed: Boolean,
)

data class ItemSearchDTO(
    @SerializedName("itemId") val id: Int,
    @SerializedName("pic") val img: String,
    @SerializedName("categoryName") val category: String,
    @SerializedName("martShopName") val store: String,
    @SerializedName("itemName") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("content") val content: String,
    @SerializedName("like") var isLiked: Boolean,
)