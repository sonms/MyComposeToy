package com.example.mycomposetoy.data.service

import com.example.mycomposetoy.data.dto.remote.response.auth.LoginRequestDto
import com.example.mycomposetoy.data.dto.remote.response.auth.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun signIn(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto?>
}