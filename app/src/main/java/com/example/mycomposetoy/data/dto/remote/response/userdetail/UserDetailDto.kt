package com.example.mycomposetoy.data.dto.remote.response.userdetail

import com.example.mycomposetoy.data.dto.remote.response.user.UserListDto
import com.example.mycomposetoy.domain.entity.UserDetailEntity
import com.example.mycomposetoy.domain.entity.UserDetailSupportEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailDto(
    @SerialName("data")
    val data : UserListDto,
    @SerialName("support")
    val support : UserDetailSupportDto
) {
    fun toDomain() = UserDetailEntity(
        data = data.toDomain(),
        support = support.toDomain()
    )
}

@Serializable
data class UserDetailSupportDto(
    @SerialName("url")
    val url : String,
    @SerialName("text")
    val text : String
) {
    fun toDomain() = UserDetailSupportEntity(
        url = url,
        text = text
    )
}
