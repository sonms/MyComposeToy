package com.example.mycomposetoy.presentation.userdetail.state

import androidx.compose.runtime.Immutable
import com.example.mycomposetoy.core.util.UiState
import com.example.mycomposetoy.presentation.userdetail.model.UserDetailUiModel

@Immutable
data class UserDetailState(
    val userDetail : UiState<UserDetailUiModel> = UiState.Empty,
)
