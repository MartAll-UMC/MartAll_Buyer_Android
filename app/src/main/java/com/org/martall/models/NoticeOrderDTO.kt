package com.org.martall.models

data class NoticeOrderDTO(
    val timeStamp: String,
    val status: Int,
    val message: String,
    val result: MartResult,
    val success: Boolean
){
    data class MartResult(
        val mart: Mart,
        val cartItems: List<CartItem>
    ){
        data class Mart(
            val martShopId: Int,
            val martName: String,
            val martCategory: List<MartCategory>,
            val bookmarkCount: Int,
            val likeCount: Int
        ){
            data class MartCategory(
                val categoryName: String,
                val index: String
            )
        }

        data class CartItem(
            val cartItemId: Int,
            val itemId: Int,
            val categoryName: String,
            val picName: String,
            val count: Int,
            val itemName: String,
            val price: Int
        )

    }

}
