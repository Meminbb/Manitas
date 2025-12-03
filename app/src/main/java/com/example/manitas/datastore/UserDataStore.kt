package com.example.manitas.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

object UserDataStore {
    private val Context.dataStore by preferencesDataStore("user_prefs")

    private val USER_ID = stringPreferencesKey("user_id")

    suspend fun saveUserId(context: Context, id: String?) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = id as String
        }
    }

    fun getUserId(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID]
        }
    }
}
