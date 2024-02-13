package com.org.martall.model

import com.google.gson.annotations.SerializedName

data class FollowResponseDTO(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("bookmarkId") val bookmarkId: Int,
    @SerializedName("martShopId") val martShopId: Int,
    @SerializedName("userIdx") val userIdx: String
)