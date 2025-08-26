package com.example.mycomposetoy.data.repositoryimpl

import com.example.mycomposetoy.data.datasource.remote.AuthRemoteDataSource
import com.example.mycomposetoy.domain.entity.auth.TokenEntity
import com.example.mycomposetoy.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<TokenEntity?> = runCatching {
        val loginResponseDto = remoteDataSource.signIn(email, password)
        val tokenEntity = loginResponseDto?.toDomain()

        tokenEntity
    }
}