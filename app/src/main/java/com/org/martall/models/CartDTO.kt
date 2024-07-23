package com.org.martall.models

import com.google.gson.annotations.SerializedName


data class CartResponseDTO(
    @SerializedName("status") val cartId: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CartResult,
)

data class CartResult(
    @SerializedName("mart") val mart: CartMart,
    @SerializedName("cartItemResponseList") val products: List<CartProduct>,
)

data class CartMart(
    @SerializedName("martShopId") val id: Int?,
    @SerializedName("martName") val name: String?,
    @SerializedName("martCategory") val category: List<MartCategory>?,
    @SerializedName("bookmarkCount") val bookmarkCnt: Int,
    @SerializedName("likeCount") val likeCnt: Int,
)

data class CartProduct(
    @SerializedName("cartItemId") val cartId: Int,
    @SerializedName("productId") val id: Int,
    @SerializedName("categoryName") val category: String,
    @SerializedName("itemName") val name: String,
    @SerializedName("count") val count: Int = 1,
    @SerializedName("itemPrice") val price: Int,
    @SerializedName("picName") val img: String,
)

data class MartCategory(
    @SerializedName("categoryName") val name: String,
    @SerializedName("index") val idx: Int,
)