package com.example.mycomposetoy.domain.repository

import com.example.mycomposetoy.domain.entity.auth.TokenEntity

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<TokenEntity?>
}