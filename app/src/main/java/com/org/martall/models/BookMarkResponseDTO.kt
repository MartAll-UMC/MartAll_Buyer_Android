package com.org.martall.models

data class BookMarkResponseDTO(
    val message: String,
    val status: Int,
    val followedMarts: List<DibsMarts>
) {
    data class DibsMarts(
        val bookmarkId: Int,
        val martcategory: List<String>,
        val martname: String,
        val martshopId: Int,
        val salesIndex: Int,
        val visitorCount: Int,
        var isfollowed: Boolean = true)
    }