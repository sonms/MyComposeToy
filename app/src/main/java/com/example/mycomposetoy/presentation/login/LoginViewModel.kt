package com.example.mycomposetoy.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposetoy.domain.usecase.PostSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: PostSignInUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInState())
    val uiState : StateFlow<SignInState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignInSideEffect>()
    val sideEffect: SharedFlow<SignInSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(
                email = email,
                password = password
            ).onSuccess { data ->
                if (data != null) {
                    _sideEffect.emit(SignInSideEffect.NavigateToHome)
                } else {
                    _sideEffect.emit(SignInSideEffect.ShowSnackBar("로그인 실패"))
                }
            }
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }
}