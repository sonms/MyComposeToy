package com.example.mycomposetoy.presentation.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.mycomposetoy.core.navigation.MainTapRoute
import com.example.mycomposetoy.core.navigation.Route
import com.example.mycomposetoy.presentation.home.HomeRoute
import com.example.mycomposetoy.presentation.login.LoginRoute
import com.example.mycomposetoy.presentation.product.ProductRoute
import com.example.mycomposetoy.presentation.profile.ProfileRoute
import com.example.mycomposetoy.presentation.user.UserListRoute
import com.example.mycomposetoy.presentation.userdetail.UserDetailRoute

//애니메이션 충돌 Nav3 라이브러리
//private const val SCREEN_TRANSITION_DURATION = 300

@Composable
fun MainScreen(

) {
    MainScreenContent()
}

/*@Composable
fun MainScreenContent() {
    val backStack = rememberNavBackStack(MainTapRoute.Home)

    val currentKey = backStack.last()

    val currentTab = MainTab.entries.find { it.route == currentKey }

    Scaffold(
        bottomBar = {
            MainBottomBar(
                isVisible = currentTab != null,
                currentTab = currentTab,
                tabs = MainTab.entries,
                onTabSelect = { selectedTab ->
                    if (selectedTab.route != backStack.last()) {
                        backStack.add(selectedTab.route)
                    }
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<MainTapRoute.Home> {
                    HomeRoute(
                        navigateToUserList = {
                            backStack.add(Route.UserList)
                        }
                    )
                }
                entry<MainTapRoute.Product> {
                    ProductRoute()
                }
                entry<MainTapRoute.Profile> {
                    ProfileRoute()
                }
                entry<Route.UserList> {
                    UserListRoute(
                        navigateToDetail = { id ->
                            backStack.add(Route.UserDetail(id))
                        }
                    )
                }
                entry<Route.UserDetail> { key ->
                    UserDetailRoute(
                        id = key.id
                    )
                }
            },
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            *//*transitionSpec = {
                ContentTransform(
                    fadeIn(tween(SCREEN_TRANSITION_DURATION)),
                    fadeOut(tween(SCREEN_TRANSITION_DURATION))
                )
            },*//*
            modifier = Modifier.padding(innerPadding)
        )
    }
}*/

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier
) {
    val topLevelBackStack = remember { TopLevelBackStack<NavKey>(MainTapRoute.Home) }

    // 현재 라우트 가져오기
    val currentEntry = topLevelBackStack.currentTopLevelKey

    // 현재 라우트가 탭 중 하나인지 확인
    val currentTab = MainTab.entries.find { it.route == currentEntry }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier
            .safeDrawingPadding(),
        bottomBar = {
            MainBottomBar(
                isVisible = currentTab != null,
                currentTab = currentTab,
                tabs = MainTab.entries,
                onTabSelect = { selectedTab ->
                    if (selectedTab.route != topLevelBackStack.currentTopLevelKey) {
                        topLevelBackStack.switchTopLevel(selectedTab.route)
                    }
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = { route ->
                when (route) {
                    MainTapRoute.Home -> NavEntry(route) {
                        HomeRoute(
                            navigateToUserList = {
                                topLevelBackStack.add(Route.UserList)
                            }
                        )
                    }

                    MainTapRoute.Product -> NavEntry(route) {
                        ProductRoute()
                    }

                    MainTapRoute.Profile -> NavEntry(route) {
                        ProfileRoute()
                    }

                    Route.UserList -> NavEntry(route) {
                        UserListRoute(
                            navigateToDetail = { id ->
                                topLevelBackStack.add(Route.UserDetail(id))
                            }
                        )
                    }

                    Route.Login -> NavEntry(route) {
                        LoginRoute(
                            navigateToHome = {
                                topLevelBackStack.switchTopLevel(MainTapRoute.Home)
                            }
                        )
                    }

                    is Route.UserDetail -> NavEntry(route) {
                        val id = route.id
                        UserDetailRoute(
                            id = id
                        )
                    }

                    else -> throw IllegalArgumentException("Unknown route: $route")
                }
            },
            entryDecorators = listOf(
                // 장면을 관리하고 상태를 저장하기 위한 기본 데코레이터를 추가합니다.
                rememberSceneSetupNavEntryDecorator(), // 라이프사이클 설정
                rememberSavedStateNavEntryDecorator(), // 상태를 저장합니다
                // 그런 다음 뷰 모델 스토어 데코레이터를 추가합니다.
                rememberViewModelStoreNavEntryDecorator()
            ),
            /*transitionSpec = {
                val contentTransform = ContentTransform(
                    fadeIn(tween(SCREEN_TRANSITION_DURATION)),
                    fadeOut(tween(SCREEN_TRANSITION_DURATION))
                )
                contentTransform
            },*/

            modifier = Modifier
                .padding(innerPadding)
                .safeContentPadding()
        )
    }
}
