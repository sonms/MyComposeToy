package com.example.mycomposetoy.presentation.userdetail.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedAppBar(
    profileImg : String,
    profileName : String,
    visible: Boolean,
    navigateUp : () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            // 접힌 앱 바의 뒤로 아이콘
            IconButton(
                onClick = navigateUp
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween()),
                exit = fadeOut(animationSpec = tween())
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy( 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = profileImg,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = profileName,
                        color = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            // 확장된 상태의 배경색을 투명하게 설정
            containerColor = Color.Transparent,
            // 축소된 상태의 배경색을 투명하게 설정
            scrolledContainerColor = Color.Transparent,
            // 탐색 아이콘 색상 설정
            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun CollapsedAppBarPreview() {
    CollapsedAppBar(
        profileImg = "https://reqres.in/img/faces/1-image.jpg",
        profileName = "George",
        navigateUp = {},
        visible = true
    )
}