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
    val pic: String,
    val categoryName: String,
    val martShopName: String,
    val itemName: String,
    val price: Int,
    val content: String,
    var like: Boolean,
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
    val pic: String,
    val martShopName: String,
    val itemName: String,
    val price: Int,
    var like: Boolean,
)


