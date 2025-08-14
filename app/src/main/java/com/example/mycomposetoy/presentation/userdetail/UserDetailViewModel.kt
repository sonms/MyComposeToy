package com.example.mycomposetoy.presentation.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposetoy.core.state.UiState
import com.example.mycomposetoy.domain.usecase.GetUserDetailUseCase
import com.example.mycomposetoy.presentation.userdetail.model.UserDetailUiModel
import com.example.mycomposetoy.presentation.userdetail.model.toDetailUiModel
import com.example.mycomposetoy.presentation.userdetail.model.toUiModel
import com.example.mycomposetoy.presentation.userdetail.state.UserDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    //savedStateHandle: SavedStateHandle,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    //private val route = savedStateHandle.toRoute<Route.UserDetail>()

    private val _uiState = MutableStateFlow(UserDetailState())
    val uiState : StateFlow<UserDetailState> = _uiState.asStateFlow()

    fun getUserDetail(id : Int) {
        viewModelScope.launch {
            getUserDetailUseCase(id)
                .onSuccess { result ->
                    _uiState.update {
                        it.copy(
                            userDetail = UiState.Success(
                                UserDetailUiModel(
                                    data = result.data.toDetailUiModel(),
                                    support = result.support.toUiModel()
                                )
                            )
                        )
                    }
                }
                .onFailure { result ->
                    _uiState.update {
                        it.copy(
                            userDetail = UiState.Failure(result.message.toString())
                        )
                    }
                }
        }
    }
}