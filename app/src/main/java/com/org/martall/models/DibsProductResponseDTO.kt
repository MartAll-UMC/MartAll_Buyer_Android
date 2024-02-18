package com.org.martall.models

data class DibsProductResponseDTO(
    val message: String,
    val status: Int,
    val success: Boolean,
    val timeStamp: String,
    val `data`: List<DibsProducts>,
) {
    data class DibsProducts(
        val categoryId: Int,
        val content: String,
        val itemId: Int,
        val itemName: String,
        val price: Int,
        var isLiked: Boolean = false)
}