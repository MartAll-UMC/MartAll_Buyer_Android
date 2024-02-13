package com.org.martall.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("searchResults") val marts: List<MartSimpleDTO>,
)

data class MartSimpleDTO(
    @SerializedName("name") val name: String,
    @SerializedName("martcategory") val categories: List<String>,
    @SerializedName("followersCount") val followerCnt: Int,
    @SerializedName("visitorsCount") val likeCnt: Int,
    @SerializedName("isFavorite") val isFollowed: Boolean,
)
