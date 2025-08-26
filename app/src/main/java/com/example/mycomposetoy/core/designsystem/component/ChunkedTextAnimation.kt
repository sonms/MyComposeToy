package com.example.mycomposetoy.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import com.example.mycomposetoy.core.ui.ChunkedTextAnimationState

// 실제 구현
@Composable
fun ChunkedTextAnimation(
    state: ChunkedTextAnimationState,
    modifier: Modifier = Modifier,
    chunkAnimation: (index: Int) -> EnterTransition = { fadeIn(tween(300)) + scaleIn(tween(300)) },
) {
    val density = LocalDensity.current
    val boxSizeDp = remember(density, state.boxSize) {
        with(density) { DpSize(state.boxSize.width.toDp(), state.boxSize.height.toDp()) }
    }

    Box(
        modifier = modifier.size(boxSizeDp)
    ) {
        state.chunksToDisplay.forEachIndexed { index, (text, offset) ->
            // animated visibility를 제어하기 위함이며 각 청크가 자신만의 visibleState를 가지고
            // 이것을 상태 변화를 감지하는 MutableTransitionState에 전달
            val visibleState = remember {
                MutableTransitionState(false).apply {
                    targetState = true
                }
            }

            AnimatedVisibility(
                visibleState = visibleState,
                enter = chunkAnimation(index),
                exit = ExitTransition.None,
                modifier = Modifier.offset {
                    IntOffset(
                        offset.x.toInt(),
                        offset.y.toInt())
                }
            ) {
                Text(
                    text = text,
                    style = state.textStyle
                )
            }
        }
    }
}