package com.example.mycomposetoy.presentation.userdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mycomposetoy.core.state.UiState
import com.example.mycomposetoy.presentation.userdetail.model.UserDetailDataUiModel
import com.example.mycomposetoy.presentation.userdetail.model.UserDetailSupportUiModel

@Composable
fun UserDetailRoute(
    id: Int,
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserDetail(id)
    }

    with(uiState) {
        when (userDetail) {
            is UiState.Success -> {
                UserDetailScreen(
                    modifier = modifier,
                    userDetail = userDetail.data.data,
                    userSupport = userDetail.data.support
                )
            }

            is UiState.Failure -> {
                Text(text = userDetail.msg)
            }

            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(
                            color = Color.Black.copy(alpha = 0.5f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Empty -> {

            }
        }
    }
}

@Composable
fun UserDetailScreen(
    userDetail: UserDetailDataUiModel,
    userSupport: UserDetailSupportUiModel,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "User Detail Screen")

        AsyncImage(
            model = userDetail.avatar,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )

        Text(text = userDetail.email)
        Text(text = userDetail.firstName)
        Text(text = userDetail.lastName)

        Text(text = userSupport.supportUrl)
        Text(text = userSupport.supportText)
    }
}

@Preview
@Composable
private fun UserDetailRoutePreview() {
    UserDetailRoute(
        id = 1
    )
}