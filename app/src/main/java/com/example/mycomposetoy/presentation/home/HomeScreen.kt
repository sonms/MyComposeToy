package com.example.mycomposetoy.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeRoute(

) {
    HomeScreen()
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Text(
            text = "home",
            color = Color.Black
        )
    }
}