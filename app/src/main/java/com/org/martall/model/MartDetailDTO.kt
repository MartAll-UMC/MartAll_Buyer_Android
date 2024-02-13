package com.org.martall.model

import com.google.gson.annotations.SerializedName

data class MartDetailDTO(
    @SerializedName("martshopId") val martshopId: Int,
    @SerializedName("name") val name: String,
    @SerializedName ("shopnumber") val shopnumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("phonenumber") val phonenumber: String,
    @SerializedName("address") val address: String,
    @SerializedName("operatingHours") val operatingHours: String,
    @SerializedName("pickuptime") val pickuptime: String,
    @SerializedName("payment") val payment: String,
    @SerializedName("kakaoTalkLink") val kakaoTalkLink: String
)
