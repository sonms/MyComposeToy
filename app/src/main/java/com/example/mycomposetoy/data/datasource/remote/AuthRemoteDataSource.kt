package com.example.mycomposetoy.data.datasource.remote

import com.example.mycomposetoy.data.dto.remote.response.auth.LoginRequestDto
import com.example.mycomposetoy.data.dto.remote.response.auth.LoginResponseDto
import com.example.mycomposetoy.data.service.AuthService
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val service: AuthService
) {
    suspend fun signIn(email: String, password: String) : LoginResponseDto? {
        val response = service.signIn(LoginRequestDto(email, password))

        if (response.isSuccessful && response.body() != null) {
            return response.body()
        } else {
            throw Exception("로그인에 실패했습니다. (코드: ${response.code()})")
        }
    }
}