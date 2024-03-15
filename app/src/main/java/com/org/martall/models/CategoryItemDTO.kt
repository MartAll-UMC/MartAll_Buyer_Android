package com.org.martall.models

data class CategoryResponse(
    val timeStamp: String,
    val status: Int,
    val message: String,
    val success: Boolean,
    val result: Result
) {
    data class Result(
        val items: List<Item>
    )

    data class Item(
        val itemId: Int,
        val pic: String,
        val martShopName: String,
        val itemName: String,
        val price: Int,
        val like: String
    )
}
