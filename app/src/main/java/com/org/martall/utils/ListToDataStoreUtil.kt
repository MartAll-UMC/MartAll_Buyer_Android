package com.org.martall.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListToDataStoreUtil {
    suspend fun saveList(dataStore: DataStore<Preferences>, key: String, list: List<String>) {
        val jsonString = Json.encodeToString(list)
        dataStore.edit { pref ->
            pref[stringPreferencesKey(key)] = jsonString
        }
    }

    suspend fun getList(dataStore: DataStore<Preferences>, key: String): List<String> {
        val jsonString = dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)] ?: "[]"
        }.first()

        return Json.decodeFromString<List<String>>(jsonString)
    }
}