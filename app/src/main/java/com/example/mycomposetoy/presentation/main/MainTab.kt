package com.example.mycomposetoy.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.example.mycomposetoy.R
import com.example.mycomposetoy.core.navigation.MainTapRoute
import com.example.mycomposetoy.core.navigation.Route


enum class MainTab(
    @param:DrawableRes val selectedIcon: Int,
    @param:DrawableRes val unselectedIcon: Int,
    @param:StringRes val contentDescription: Int,
    val route: MainTapRoute,
) {
    HOME(
        selectedIcon = R.drawable.baseline_home_filled_24,
        unselectedIcon = R.drawable.baseline_home_filled_24,
        contentDescription = R.string.home,
        route = MainTapRoute.Home,
    ),
    Product(
        selectedIcon = R.drawable.baseline_home_filled_24,
        unselectedIcon = R.drawable.baseline_home_filled_24,
        contentDescription = R.string.home,
        route = MainTapRoute.Product,
    ),
    Profile(
        selectedIcon = R.drawable.baseline_home_filled_24,
        unselectedIcon = R.drawable.baseline_home_filled_24,
        contentDescription = R.string.home,
        route = MainTapRoute.Profile,
    ),
    ;

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTapRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun fromNavKey(navKey: NavKey?): MainTab? {
            return entries.find { it.route == navKey }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}