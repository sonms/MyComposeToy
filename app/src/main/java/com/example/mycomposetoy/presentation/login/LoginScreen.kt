package com.example.mycomposetoy.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SignInSideEffect.ShowSnackBar -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }

                is SignInSideEffect.NavigateToHome -> {
                    navigateToHome()
                }

                is SignInSideEffect.NavigateToSignUp -> {
                    // TODO
                }
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        emailFromUi = uiState.email,
        passwordFromUi = uiState.password,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        signIn = {
            // 클릭 순간 상태 값 캡처
            viewModel.signIn(
                email = uiState.email,
                password = uiState.password
            )
        }
    )
}

@Composable
fun LoginScreen(
    signIn : () -> Unit,
    emailFromUi : String,
    passwordFromUi : String,
    onEmailChanged : (String) -> Unit,
    onPasswordChanged : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        TextField(
            value = emailFromUi,
            onValueChange = { onEmailChanged(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = passwordFromUi,
            onValueChange = { onPasswordChanged(it) }
        )

        Button(
            onClick = signIn
        ) {
            Text("로그인")
        }
    }
}