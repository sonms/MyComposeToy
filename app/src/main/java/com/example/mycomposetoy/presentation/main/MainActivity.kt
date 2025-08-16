package com.example.mycomposetoy.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mycomposetoy.core.designsystem.theme.MyComposeToyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /*@Inject
    lateinit var systemBarsColorController: SystemBarsColorController
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyComposeToyTheme {
                /*CompositionLocalProvider(
                    LocalSystemBarsColor provides systemBarsColorController
                ) {

                }*/
                MainScreen()
            }
        }
    }
}