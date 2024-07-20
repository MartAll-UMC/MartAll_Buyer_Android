package com.org.martall.services

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.org.martall.view.search.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant

class MartAllUserInfoManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val accessTokenKey = stringPreferencesKey("appAccessToken")
        val refreshTokenKey = stringPreferencesKey("appRefreshToken")
        val accessTokenExpireKey = stringPreferencesKey("appAccessTokenExpire")
        val refreshTokenExpireKey = stringPreferencesKey("appRefreshTokenExpire")
    }

    suspend fun isValidToken() : Boolean {
        return dataStore.data.map { prefs ->
            val accessToken = prefs[accessTokenKey]
            val refreshToken = prefs[refreshTokenKey]
            val accessTokenExpire = prefs[accessTokenExpireKey]
            val refreshTokenExpire = prefs[refreshTokenExpireKey]

            accessToken != "" && (accessToken != null && refreshToken != null && accessTokenExpire != null && refreshTokenExpire != null) && Instant.parse(
                refreshTokenExpire
            )> Instant.now()
        }.first()
    }

    suspend fun needRefreshToken(): Boolean {
        return dataStore.data.map { prefs ->
            val accessTokenExpireKey = prefs[accessTokenExpireKey]
            Instant.parse(accessTokenExpireKey) < Instant.now()
        }.first()
    }

    suspend fun getToken(): Pair<String?, String?> {
        return dataStore.data.map { prefs ->
            val accessToken = prefs[accessTokenKey]
            val refreshToken = prefs[refreshTokenKey]
            accessToken to refreshToken
        }.first()
    }

    suspend fun updateTokens(
        accessToken: String,
        refreshToken: String,
        accessTokenExpire: String,
        refreshTokenExpire: String,
    ) {
        dataStore.edit { prefs ->
            prefs[accessTokenKey] = accessToken
            prefs[refreshTokenKey] = refreshToken
            prefs[accessTokenExpireKey] = accessTokenExpire
            prefs[refreshTokenExpireKey] = refreshTokenExpire
        }
    }

    suspend fun updateAccessToken(
        accessToken: String,
        accessTokenExpire: String,
    ) {
        dataStore.edit { prefs ->
            prefs[accessTokenKey] = accessToken
            prefs[accessTokenExpireKey] = accessTokenExpire
        }
    }
}