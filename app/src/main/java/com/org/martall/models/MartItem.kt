package com.org.martall.models

data class Response(
    val timeStamp: String,
    val status: Int,
    val message: String,
    val result: List<Item>,
    var success: Boolean,
)

data class Item(
    val itemId: Int,
    val itemImg: String,
    val categoryName: String,
    val martShopName: String,
    val itemName: String,
    val itemPrice: Int,
    val content: String,
    var itemLike: Boolean,
)

data class SecondResponse(
    val timeStamp: String,
    val status: Int,
    val message: String,
    val result: List<SecondItem>,
    var success: Boolean,
)

data class SecondItem(
    val itemId: Int,
    val itemImg: String,
    val mart: CategoryMartDTO,
    val itemName: String,
    val itemPrice: Int,
    var itemLike: Boolean,
)
data class CategoryMartDTO(
    val martId: Int,
    val martName: String,
    val martCategory: List<String>,
    val bookmarkCount: Int,
    val likeCount: Int,
    val martBookmark: Boolean
)

