package com.example.mycomposetoy.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycomposetoy.core.state.UiState
import com.example.mycomposetoy.presentation.user.component.UserItem
import com.example.mycomposetoy.presentation.user.model.UserListUiModel

@Composable
fun UserListRoute(
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val userListState = uiState.userListData) {
        is UiState.Success -> {
            UserListScreen(
                modifier = modifier,
                userList = userListState.data
            )
        }
        is UiState.Failure -> {
            Text(text = userListState.msg)
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

@Composable
fun UserListScreen(
    userList : List<UserListUiModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn (
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        itemsIndexed(
            items = userList
        ) { index, item ->
            UserItem(
                /*id = item.id,
                email = item.email,
                firstName = item.firstName,
                lastName = item.lastName,
                avatar = item.avatar*/
                item = item
            )
        }
    }
}