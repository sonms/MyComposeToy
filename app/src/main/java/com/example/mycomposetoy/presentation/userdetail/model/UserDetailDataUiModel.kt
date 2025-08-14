package com.example.mycomposetoy.presentation.userdetail.model

import androidx.compose.runtime.Immutable
import com.example.mycomposetoy.domain.entity.UserListEntity

@Immutable
data class UserDetailDataUiModel(
    val id: Int = 1,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
)

fun UserListEntity.toDetailUiModel(): UserDetailDataUiModel {
    return UserDetailDataUiModel(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar,
    )
}