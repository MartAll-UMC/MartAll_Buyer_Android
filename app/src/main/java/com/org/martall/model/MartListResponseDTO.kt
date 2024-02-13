package com.org.martall.model

import com.google.gson.annotations.SerializedName

data class MartListResponseDTO(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("marts") val marts: List<MartDataDTO>
)
