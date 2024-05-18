package com.org.martall.models;

import com.google.gson.annotations.SerializedName;
import kotlinx.serialization.InternalSerializationApi

data class SignupResponse (
        @SerializedName("timeStamp") val timeStamp: String,
        @SerializedName("status") val status: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: SignDTO,
        @SerializedName("success") val success: Boolean
)

data class SignDTO (
        @SerializedName("dupCheck") var dupCheck: Boolean
)

