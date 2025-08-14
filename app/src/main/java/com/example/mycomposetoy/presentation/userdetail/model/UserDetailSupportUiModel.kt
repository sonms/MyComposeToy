package com.example.mycomposetoy.presentation.userdetail.model

import androidx.compose.runtime.Immutable
import com.example.mycomposetoy.domain.entity.UserDetailSupportEntity

@Immutable
data class UserDetailSupportUiModel(
    val supportUrl: String = "",
    val supportText: String = "",
)

fun UserDetailSupportEntity.toUiModel(): UserDetailSupportUiModel {
    return UserDetailSupportUiModel(
        supportUrl = this.url,
        supportText = this.text,
    )
}
