package com.example.mycomposetoy.presentation.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposetoy.core.state.UiState
import com.example.mycomposetoy.domain.usecase.GetUserListUseCase
import com.example.mycomposetoy.presentation.user.model.toUiModel
import com.example.mycomposetoy.presentation.user.state.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserListState())
    val uiState : StateFlow<UserListState> = _uiState.asStateFlow()

    init {
        getUserList(uiState.value.currentPage)
    }

    fun updatePage(page : Int) {
        viewModelScope.launch {
            val currentState = uiState.value
            val newPage = currentState.currentPage + page

            if (newPage < 1 || newPage > currentState.totalPage) {
                return@launch
            }

            getUserList(newPage)
        }
    }

    /*fun updateState(reducer: UserListState.() -> UserListState) {
        _uiState.update {
            it.reducer()
        }
    }*/

    fun getUserList(page: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(userListData = UiState.Loading) }

            getUserListUseCase(page)
                .onSuccess { data ->
                    _uiState.update { state ->
                        state.copy(
                            userListData = UiState.Success(
                                data.userList.map { it.toUiModel() }
                            ),
                            currentPage = data.currentPage,
                            totalPage = data.totalPages,
                            canLoadMore = data.currentPage <= data.totalPages
                        )
                    }
                    Log.d("TAG", "getUserList: $data")
                }
                .onFailure { failure ->
                    _uiState.update {
                        it.copy(
                            userListData = UiState.Failure(failure.message ?: "")
                        )
                    }
                    Log.d("TAG", "getUserList: $failure")
                }
        }
    }
}