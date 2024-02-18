package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val results: LoginDTO,
)
data class LoginRequest(
    @SerializedName("username") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("imgUrl") val img: String,
    @SerializedName("providerId") val providerId: String,
    @SerializedName("provider") val provider: String = "kakao",
    @SerializedName("userType") val userType: Int = 1,
)
data class LoginDTO(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_expiredDate") val accessTokenExpiredDate: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_expiredDate") val refreshTokenExpiredDate: String,
)

data class RefreshResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: RefreshDTO,
)

data class RefreshDTO(
    @SerializedName("token") val accessToken: String,
    @SerializedName("expiredDate") val accessTokenExpiredDate: String,
)