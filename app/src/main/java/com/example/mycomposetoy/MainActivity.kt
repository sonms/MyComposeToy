package com.example.mycomposetoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mycomposetoy.core.designsystem.theme.MyComposeToyTheme
import com.example.mycomposetoy.presentation.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyComposeToyTheme {
                MainScreen()
            }
        }
    }
}