package com.example.mycomposetoy.data.datasourceimpl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mycomposetoy.data.datasource.local.TokenDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : TokenDataSource {
    override fun getAccessToken(): Flow<String> = dataStore.data
        .map { preferences -> preferences[ACCESS_TOKEN] ?: "" }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }

        Log.d("TokenDataSourceImpl", "saveAccessToken: $token")
    }

    override suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }
}