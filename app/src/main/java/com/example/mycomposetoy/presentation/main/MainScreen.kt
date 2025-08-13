package com.example.mycomposetoy.presentation.main

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.mycomposetoy.core.navigation.MainTapRoute
import com.example.mycomposetoy.presentation.home.HomeRoute
import com.example.mycomposetoy.presentation.product.ProductRoute
import com.example.mycomposetoy.presentation.profile.ProfileRoute
import com.example.mycomposetoy.presentation.user.UserListRoute
import com.example.mycomposetoy.presentation.user.navigation.UserList

private const val SCREEN_TRANSITION_DURATION = 300

@Composable
fun MainScreen(

) {
    MainScreenContent()
}

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
        modifier = modifier,
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
            entryProvider = { route  ->
                when (route) {
                    MainTapRoute.Home -> NavEntry(route ) {
                        HomeRoute(
                            navigateToUserList = {
                                topLevelBackStack.add(UserList)
                            }
                        )
                    }
                    MainTapRoute.Product -> NavEntry(route ) {
                        ProductRoute()
                    }
                    MainTapRoute.Profile -> NavEntry(route ) {
                        ProfileRoute()
                    }

                    UserList -> NavEntry(route ) {
                        UserListRoute()
                    }
                    else -> throw IllegalArgumentException("Unknown route: $route")
                }
            }/*entryProvider {
                MainTab.entries.forEach { tab ->
                    entry(tab.route) { navKey ->
                        when (navKey) {
                            MainTapRoute.Home -> HomeRoute()
                            MainTapRoute.Product -> ProductRoute()
                            MainTapRoute.Profile -> ProfileRoute()
                        }
                    }
                }
            }*/,
            entryDecorators = listOf(
                // 장면을 관리하고 상태를 저장하기 위한 기본 데코레이터를 추가합니다.
                rememberSceneSetupNavEntryDecorator(), // 라이프사이클 설정
                rememberSavedStateNavEntryDecorator(), // 상태를 저장합니다
                // 그런 다음 뷰 모델 스토어 데코레이터를 추가합니다.
                rememberViewModelStoreNavEntryDecorator()
            ),
            transitionSpec = {
                val contentTransform = ContentTransform(
                    fadeIn(tween(SCREEN_TRANSITION_DURATION)),
                    fadeOut(tween(SCREEN_TRANSITION_DURATION))
                )
                contentTransform
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}