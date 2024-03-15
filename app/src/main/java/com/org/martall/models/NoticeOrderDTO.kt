package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class NoticeOrderDTO(
    val timeStamp: String,
    val status: Int,
    val message: String,
    val result: MartResult,
    val success: Boolean,
) {
    data class MartResult(
        @SerializedName("order") val mart: Mart,
        @SerializedName("orderItem") val cartItems: List<CartItem>,
    ) {
        data class Mart(
            val martShopId: Int,
            val martName: String,
            val martCategory: List<MartCategory>,
            val bookmarkCount: Int,
            val likeCount: Int,
        ) {
            data class MartCategory(
                val categoryName: String,
                val index: String,
            )
        }

        data class CartItem(
            val cartItemId: Int,
            val itemId: Int,
            val categoryName: String,
            val picName: String,
            val count: Int,
            val itemName: String,
            val price: Int,
        )

    }

}
