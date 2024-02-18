package com.org.martall.model

data class DibsMartUnfollowDTO(
    val status: Int,
    val message: String,
    val bookmarkId: Int,
    val martShopId: Int,
    val userIdx: String,
    var isfollowed: Boolean = false
)
