package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class BookMarkResponseDTO(
    @SerializedName("timeStamp") val timeStamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<DibsMarts>
) {
    data class DibsMarts(
        @SerializedName("martShopId") val martId: Int,
        @SerializedName("martImg") val martImg: String,
        @SerializedName("martName") val martName: String,
        @SerializedName("martCategory") val martCategory: List<String>,
        @SerializedName("bookmarkCount") val bookmarkCount: Int,
        @SerializedName("likeCount") val likeCount: Int,
        @SerializedName("followed") val martBookmark: Boolean
    )
}
