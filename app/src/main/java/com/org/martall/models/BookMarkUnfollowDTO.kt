package com.org.martall.models

data class BookMarkUnfollowDTO(
    val status: Int,
    val message: String,
    val bookmarkId: Int,
    val martShopId: Int,
    val userIdx: String,
    var isfollowed: Boolean = false
)
