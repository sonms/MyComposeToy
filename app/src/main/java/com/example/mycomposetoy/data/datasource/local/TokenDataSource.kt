package com.example.mycomposetoy.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface TokenDataSource {
    /*fun getAccessToken(): Flow<String?>
    fun getRefreshToken(): Flow<String?>
    suspend fun saveTokens(token: AuthEntity)
    suspend fun clearTokens()*/

    fun getAccessToken(): Flow<String>
    suspend fun saveAccessToken(token: String)
    suspend fun clearAccessToken()
}