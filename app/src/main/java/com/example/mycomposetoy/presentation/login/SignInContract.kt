package com.example.mycomposetoy.presentation.login

import androidx.compose.runtime.Immutable

@Immutable
data class SignInState(
    val email: String = "",
    val password: String = "",
)

sealed class SignInSideEffect {
    data class ShowSnackBar(val message: String) : SignInSideEffect()
    data object NavigateToSignUp : SignInSideEffect()
    data object NavigateToHome : SignInSideEffect()
    /*data object StartKakaoTalkLogin : SignInSideEffect()
    data object StartKakaoWebLogin : SignInSideEffect()*/
}