package com.example.mycomposetoy.presentation.product

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomposetoy.core.designsystem.component.ChunkedTextAnimation
import com.example.mycomposetoy.core.ui.ChunkedTextAnimationState
import com.example.mycomposetoy.core.ui.LineByLineAnimationState
import com.example.mycomposetoy.core.ui.WordByWordAnimationState
import com.example.mycomposetoy.core.util.InnerScrollablesState
import com.example.mycomposetoy.core.util.NestedVerticalGridsScrollConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductRoute(

) {
    val textMeasurer = rememberTextMeasurer()
    val screenWidthPx = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }
    val textStyle = LocalTextStyle.current.copy(
        fontSize = 20.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Start
    )

    val wordByWordState = remember {
        WordByWordAnimationState(textMeasurer, textStyle)
    }

    val lineByLineState = remember {
        LineByLineAnimationState(textMeasurer, textStyle)
    }

    LaunchedEffect(Unit) {
        launch {
            wordByWordState.loadText(
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry that has been the standard ever since the 1500s.",
                constraints = Constraints(maxWidth = screenWidthPx)
            )

            delay(1000)

            wordByWordState.showText(100)
        }
        launch {
            lineByLineState.loadText(
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                constraints = Constraints(maxWidth = screenWidthPx)
            )

            delay(1000)

            lineByLineState.showText(300)
        }
    }

    ProductScreen(
        lineByLineState = lineByLineState,
        wordByWordState = wordByWordState
    )
}

@Composable
fun ProductScreen(
    lineByLineState: LineByLineAnimationState,
    wordByWordState: WordByWordAnimationState,
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

        ChunkedTextAnimation(
            state = wordByWordState,
            chunkAnimation = {
                fadeIn(tween(350, easing = LinearEasing))
            }
        )

        ChunkedTextAnimation(
            state = lineByLineState,
            chunkAnimation = {
                fadeIn(tween(350, easing = LinearEasing))
            }
        )

        // 기타 여러 바리에이션
        // fadeIn(tween(350, easing = LinearEasing)) + slideInHorizontally(tween(350))
        // slideInHorizontally(tween(350)) + fadeIn(tween(350, easing = LinearEasing))
        // slideInHorizontally(tween(350)) + scaleIn(tween(350))

        // fadeIn(tween(350)) + scaleIn(animationSpec = keyframes {
        //            durationMillis = 350
        //            0f atFraction 0f
        //            1.2f atFraction 0.8f using FastOutLinearInEasing
        //            1f atFraction 1f using LinearOutSlowInEasing
        //        })


        val lazyColumnState = rememberLazyListState()
        val innerScrollablesState = remember { InnerScrollablesState() }
        val nestedScrollConnection = remember {
            NestedVerticalGridsScrollConnection(
                lazyColumnState,
                innerScrollablesState,
            )
        }

        val viewportHeight = with(LocalDensity.current) {
            lazyColumnState.layoutInfo.viewportSize.height.toDp()
        }

        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            items(20) { index ->
                // 각 아이템은 내부 스크롤이 가능한 LazyVerticalGrid
                LazyVerticalGrid(
                    state = innerScrollablesState.getOrCreateStateForGrid(index),
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.heightIn(max = viewportHeight),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(20) { innerIndex ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .aspectRatio(1f)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Item\n$index, $innerIndex"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}