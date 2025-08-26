package com.example.mycomposetoy.presentation.user.state

import androidx.compose.runtime.Immutable
import com.example.mycomposetoy.core.util.UiState
import com.example.mycomposetoy.presentation.user.model.UserListUiModel

@Immutable
data class UserListState(
    val userListData : UiState<List<UserListUiModel>> = UiState.Empty,
    val currentPage: Int = 1,
    val totalPage : Int = 1,
    val canLoadMore: Boolean = true
)