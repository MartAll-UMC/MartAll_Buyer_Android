package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class MartListResponseDTO(
    @SerializedName("timeStamp") val timeStamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<MartDataDTO>
)
