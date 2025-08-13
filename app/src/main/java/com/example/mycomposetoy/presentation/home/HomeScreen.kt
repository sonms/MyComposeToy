package com.example.mycomposetoy.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeRoute(
    navigateToUserList : () -> Unit
) {
    HomeScreen(
        navigateToUserList = navigateToUserList
    )
}

@Composable
fun HomeScreen(
    navigateToUserList : () -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .defaultPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "텍스트를 입력하세요:"
        )

        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    innerTextField()
                }
            }
        )

        Box {
            // 컨테이너 크기에 자동 크기 조정으로 맞춤
            BasicText(
                text = "입력 텍스트: $text ",
                maxLines = 1,
                softWrap = true,
                autoSize = TextAutoSize.StepBased(),
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = navigateToUserList
        ) {
            Text(
                text = "유저 리스트로 이동"
            )
        }
    }
}

fun Modifier.defaultPadding() = padding(16.dp)

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        navigateToUserList = {}
    )
}