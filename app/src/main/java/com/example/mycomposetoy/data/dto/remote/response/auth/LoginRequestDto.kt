package com.example.mycomposetoy.data.dto.remote.response.auth

import com.example.mycomposetoy.domain.entity.auth.SignInEntity
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password : String
) {
    fun toDomain() = SignInEntity(
        email = email,
        password = password
    )
}
