package com.example.mycomposetoy.presentation.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mycomposetoy.core.state.UiState
import com.example.mycomposetoy.presentation.user.component.UserItem
import com.example.mycomposetoy.presentation.user.model.UserListUiModel

@Composable
fun UserListRoute(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val userListState = uiState.userListData) {
        is UiState.Success -> {
            UserListScreen(
                currentPage = uiState.currentPage,
                totalPage = uiState.totalPage,
                userList = userListState.data,
                modifier = modifier,
                onClickNext = {
                    viewModel.updatePage(1)
                },
                onClickPrev = {
                    viewModel.updatePage(-1)
                },
                navigateToDetail = { id ->
                    navigateToDetail(id)
                }
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
    currentPage : Int,
    totalPage : Int,
    onClickNext : () -> Unit,
    onClickPrev : () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn (
            contentPadding = PaddingValues(8.dp)
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
                    item = item,
                    onClick = {
                        navigateToDetail(item.id)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(text = "Page : $currentPage / $totalPage")

        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onClickPrev)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onClickNext)
            )
        }
    }
}