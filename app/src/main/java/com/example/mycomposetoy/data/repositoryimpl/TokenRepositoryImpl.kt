package com.example.mycomposetoy.data.repositoryimpl

import com.example.mycomposetoy.data.datasource.local.TokenDataSource
import com.example.mycomposetoy.domain.entity.auth.TokenEntity
import com.example.mycomposetoy.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource
) : TokenRepository {
    private var cachedAccessToken = ""

    override suspend fun getAccessToken(): Flow<String> = tokenDataSource.getAccessToken()

    override suspend fun saveAccessToken(token: TokenEntity) {
        tokenDataSource.saveAccessToken(token.token)
        cachedAccessToken = token.token
    }

    override suspend fun clearAccessToken() {
        cachedAccessToken = ""
        tokenDataSource.clearAccessToken()
    }

    override suspend fun initCachedAccessToken() {
        cachedAccessToken = tokenDataSource.getAccessToken().firstOrNull().orEmpty()
    }

    override fun getCachedAccessToken(): String = cachedAccessToken
}