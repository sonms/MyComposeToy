package com.example.mycomposetoy.presentation.userdetail.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserDetailUiModel(
    val data : UserDetailDataUiModel,
    val support : UserDetailSupportUiModel
)
