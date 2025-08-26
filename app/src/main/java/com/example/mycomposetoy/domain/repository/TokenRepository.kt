package com.example.mycomposetoy.domain.repository

import com.example.mycomposetoy.domain.entity.auth.TokenEntity
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun getAccessToken(): Flow<String>
    suspend fun saveAccessToken(token: TokenEntity)
    suspend fun clearAccessToken()
    suspend fun initCachedAccessToken()
    fun getCachedAccessToken() : String
}