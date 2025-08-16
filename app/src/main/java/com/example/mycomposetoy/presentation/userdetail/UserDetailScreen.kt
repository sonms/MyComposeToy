package com.example.mycomposetoy.presentation.userdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mycomposetoy.core.state.UiState
import com.example.mycomposetoy.presentation.userdetail.component.CollapsedAppBar
import com.example.mycomposetoy.presentation.userdetail.component.UserTopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userDetail: UserDetailDataUiModel,
    userSupport: UserDetailSupportUiModel,
    modifier: Modifier = Modifier,
) {
    /*Column (
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
    }*/

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val scrollState = scrollBehavior.state

    val appBarExpanded by remember {
        // 앱 바 확장 상태
        // collapsedFraction 1f = 축소됨
        // collapsedFraction 0f = 확장됨
        derivedStateOf { scrollState.collapsedFraction < 0.9f }
    }

    val expandedAppBarHeight = 180. dp

    /*val statusBarPadding = WindowInsets
        .statusBars            // 시스템 UI 중 Status Bar(상단바)의 inset 정보를 가져옴
        .asPaddingValues()     // Insets 값을 Compose가 사용하는 PaddingValues 형태로 변환
        .calculateTopPadding()*/

    Scaffold (
        //contentWindowInsets = WindowInsets(0.dp),
        modifier = modifier
            .fillMaxSize(),
            //.padding(top = statusBarPadding),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                CollapsedAppBar(
                    profileImg = userDetail.avatar,
                    profileName = "${userDetail.firstName} ${userDetail.lastName}",
                    visible = !appBarExpanded,
                    navigateUp = {
                    }
                )

                TopAppBar(
                    title = {
                        UserTopAppBar(
                            profileImg = userDetail.avatar,
                            profileName = "${userDetail.firstName} ${userDetail.lastName}",
                            content = userSupport.supportText,
                            visible = appBarExpanded
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        // 확장된 상태의 배경색을 투명하게 설정
                        containerColor = Color.Transparent,
                        // 축소된 상태의 배경색을 투명하게 설정
                        scrolledContainerColor = Color.Transparent
                    ),
                    expandedHeight = expandedAppBarHeight,
                    // 상태 표시줄 위쪽의 추가 패딩 제거
                    windowInsets = WindowInsets( 0 ),
                    scrollBehavior = scrollBehavior
                )
            }
        }
    ) { innerPadding ->
        /*Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = userSupport.supportUrl)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userSupport.supportText)
        }*/

        LazyColumn(
            modifier = Modifier
                // 중첩 스크롤 연결
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(100) {
                Text(
                    text = "$it",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun UserDetailRoutePreview() {
    UserDetailRoute(
        id = 1
    )
}