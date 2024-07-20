package com.org.martall.models;

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String,
)

data class SignUpResponse(
    @SerializedName("timeStamp") val timeStamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Any?,
    @SerializedName("success") val success: Boolean,
)

data class SignUpIdCheckResponse(
    @SerializedName("timestamp") val timeStamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: IdCheckDTO,
    @SerializedName("success") val success: Boolean,
)

data class IdCheckDTO(
    @SerializedName("idDupCheck") val idDupCheck: Boolean,
)

data class MartAllLogInRequest(
    @SerializedName("id") val id: String,
    @SerializedName("password") val password: String,
)