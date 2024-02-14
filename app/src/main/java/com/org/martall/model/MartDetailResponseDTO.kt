package com.org.martall.model

import com.google.gson.annotations.SerializedName

data class MartDetailResponseDTO(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: MartDetailDTO
)
