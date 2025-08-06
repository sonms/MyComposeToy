package com.example.mycomposetoy.core.navigation

import kotlinx.serialization.Serializable

// when으로 navdisplay에서 사용 시 모든 경우가 처리되었는지
// 컴파일러가 검증 따라서 누락 시 알려줘 안정성이 높아져서 사용
sealed interface MainTapRoute : Route {
    @Serializable
    object Home : MainTapRoute

    @Serializable
    object Product : MainTapRoute

    @Serializable
    object Profile : MainTapRoute
}