package com.org.martall.models;

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName;
import kotlinx.serialization.InternalSerializationApi

data class SignUpRequest(
        @SerializedName("name") val name: String,
        @SerializedName("id") val id: String,
        @SerializedName("password") val password: String,
        @SerializedName("email") val email: String
)

data class SignUpResponse(
        @SerializedName("timeStamp") val timeStamp: String,
        @SerializedName("status") val status: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: Any?,
        @SerializedName("success") val success: Boolean
)

data class SignUpIdCheckResponse (
        @SerializedName("timestamp")  val timeStamp: String,
        @SerializedName("status") val status: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: IdCheckDTO,
        @SerializedName("success") val success: Boolean
)

data class IdCheckDTO(
        @SerializedName("idDupCheck") val idDupCheck: Boolean
)

data class MartAllLogInRequest(
        @SerializedName("id") val id: String,
        @SerializedName("password") val password: String,
)

data class MartAllLogInResponse(
        @SerializedName("status") val status: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val results: MartAllLogInDTO,
)

data class MartAllLogInDTO(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("access_expiredDate") val accessTokenExpiredDate: String,
        @SerializedName("refresh_token") val refreshToken: String,
        @SerializedName("refresh_expiredDate") val refreshTokenExpiredDate: String,
)