package com.org.martall.models

data class DibsProductResponseDTO(
    val message: String,
    val status: Int,
    val success: Boolean,
    val timeStamp: String,
    val result: Result,
) {

    data class Result(
        val item: List<DibsProducts> // item로 수정
    )

    data class DibsProducts(
        val martShopId: Long,
        val picName: String,
        val martName: String,
        val itemId: Int,
        val itemName: String,
        val price: Int,
        var like: Boolean = true
    )
}
