package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class FollowResponseDTO(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message : String,
    @SerializedName("success") val success : Boolean,
    var like : Boolean,
)