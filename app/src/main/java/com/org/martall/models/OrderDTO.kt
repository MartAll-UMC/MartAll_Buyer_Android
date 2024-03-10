package com.org.martall.models

import com.google.gson.annotations.SerializedName
import com.org.martall.interfaces.CartApiInterface

data class OrderRequestDTO(
    @SerializedName("totalPayment") val totalPrice: Int,
    @SerializedName("martShopId") val martId: Int,
    @SerializedName("cartItemList") val carts: List<CartApiInterface.DeleteItem>,
)

data class OrderResponseDTO(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean,
)