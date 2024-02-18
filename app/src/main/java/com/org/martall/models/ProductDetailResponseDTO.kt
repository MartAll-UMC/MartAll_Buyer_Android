package com.org.martall.model

import com.google.gson.annotations.SerializedName

data class ProductDetailResponseDTO(
    @SerializedName("timeStamp") val timeStamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Results
)

data class Results (
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("mart") val mart: Mart,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("itemName") val itemName: String,
    @SerializedName("price") val price: Int,
    @SerializedName("content") val content: String,
    @SerializedName("like") var like: Boolean
)

data class Mart (
    @SerializedName("martShopId") val martShopId: Int,
    @SerializedName("martName") val martName: String,
    @SerializedName("martCategory") val martCategory: List<String>,
    @SerializedName("bookmarkCount") val bookmarkCount: Int,
    @SerializedName("likeCount") val likeCount: Int
)