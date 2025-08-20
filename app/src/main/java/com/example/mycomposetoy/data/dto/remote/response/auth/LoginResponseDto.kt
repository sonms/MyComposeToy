package com.example.mycomposetoy.data.dto.remote.response.auth

import com.example.mycomposetoy.domain.entity.auth.TokenEntity
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
) {
    fun toDomain() = TokenEntity(
        token = token
    )
}
