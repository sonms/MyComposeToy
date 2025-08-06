package com.example.mycomposetoy.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProductRoute(

) {
    ProductScreen()
}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color.Green)
    ){
        Text(
            text = "Product",
            color = Color.Black
        )
    }
}