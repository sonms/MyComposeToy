package com.example.mycomposetoy.data.dto.remote.response.user

import com.example.mycomposetoy.domain.entity.UserListEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserListDto(
    @SerialName("id")
    val id : Int,
    @SerialName("email")
    val email : String,
    @SerialName("first_name")
    val firstName : String,
    @SerialName("last_name")
    val lastName : String,
    @SerialName("avatar")
    val avatar : String,
) {
    fun toDomain() = UserListEntity(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar,
    )
}
