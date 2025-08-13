package com.example.mycomposetoy.presentation.user.model

import androidx.compose.runtime.Immutable
import com.example.mycomposetoy.domain.entity.UserListEntity

@Immutable
data class UserListUiModel(
    val id : Int = 0,
    val email : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val avatar : String = "",
)

fun UserListEntity.toUiModel(): UserListUiModel {
    return UserListUiModel(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar,
    )
}