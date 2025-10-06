package com.example.mycomposetoy.core.util

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlin.math.abs
import kotlin.math.min

// LazyColumn안에 LazyVerticalGrid같은 컴포넌트 중첩 시 스크롤 동작을 위한 용도
class NestedVerticalGridsScrollConnection(
    private val lazyColumnState: LazyListState, // 바깥 LazyColumn의 상태 제어용
    private val innerScrollableStates: InnerScrollablesState, // 내부 스크롤 컴포넌트들의 상태 목록으로 아이템 스크롤 가능한지 판단
) : NestedScrollConnection { // 부모 컴포넌트가 자식의 스크롤 이벤트 가로채기

    // 자식 컴포넌트가 스크롤 처리 전 스크롤 얼마나 가로채고 부모가 소비할지 결정
    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource,
    ): Offset {
        if (available.y == 0f) { // 수평 스크롤 무시, 수직만 처리
            return Offset.Companion.Zero
        }
        val isScrollingDown = available.y < 0
        var offsetToProcess = abs(available.y)

        // 사용자가 빠르게 스크롤 시 큰 스크롤 값이 들어올 수 있는데
        // 이 때 한 번에 처리하지 않고 나누기 위해 while 사용
        while (offsetToProcess != 0f) {
            // lazycolumn이 이미 스크롤의 맨 위나 맨 아래에 도달했는지 확인
            // 만약 더 이상 스크롤 불가시 스크롤 소비 x, 오버스크롤 효과나타나도록
            if (lazyColumnState.hasReachedScrollBoundary(isScrollingDown)) {
                // LazyColumn can not scroll anymore.
                // Don't consume offset, to display overscroll effect.
                return Offset.Companion.Zero
            }
            // 현재 화면 상태 분석, 정보 수집
            when (val gridScrollData = buildScrollData(isScrollingDown)) {
                ScrollData.NoVerticalScrollable -> {
                    return Offset.Companion.Zero
                }

                is ScrollData.VerticalScrollData -> {
                    offsetToProcess -= processOffset(
                        offset = offsetToProcess,
                        isScrollingDown = isScrollingDown,
                        scrollData = gridScrollData,
                    )
                }
            }
        }
        return available
    }

    // 현재 상태 분석 - 스크롤을 누구에게 전달할지 결정
    private fun buildScrollData(isScrollingDown: Boolean): ScrollData {
        // 현재 화면에 보이는 스크롤 가능한 내부 아이템 찾기
        val verticalScrollables = lazyColumnState.layoutInfo.visibleItemsInfo.filter {
            // innerScrollableStates[it.index] 대신 getState(it.index) 사용
            innerScrollableStates.getState(it.index) != null
        }
        if (verticalScrollables.isEmpty()) return ScrollData.NoVerticalScrollable

        // 스크롤 방향에 따라 기존이 될 아이템 정하기
        // 아래는 마지막 아이템, 위는 첫 아이템
        val scrollableToCheck = if (isScrollingDown) {
            verticalScrollables.last()
        } else {
            verticalScrollables.first()
        }
        val lazyState = innerScrollableStates.getState(scrollableToCheck.index)!!

        val canScroll = if (isScrollingDown) {
            lazyState.canScrollForward
        } else {
            lazyState.canScrollBackward
        }

        val scrollableStartOffset = scrollableToCheck.offset
        val scrollBoundaryOffset = if (isScrollingDown) {
            -lazyColumnState.layoutInfo.beforeContentPadding - lazyColumnState.layoutInfo.afterContentPadding
        } else {
            0
        }
        val desiredOffset =
            if (!canScroll && scrollableStartOffset == -lazyColumnState.layoutInfo.beforeContentPadding) {
                scrollBoundaryOffset
            } else {
                -lazyColumnState.layoutInfo.beforeContentPadding
            }

        // 필요한 모든 정보를 수집해 verticalScrollData에 담아 반환
        return ScrollData.VerticalScrollData(
            visibleVerticalItemsCount = lazyColumnState.layoutInfo.visibleItemsInfo.size,
            scrollableState = lazyState,
            diffBetweenDesiredAndCurrentOffset = abs(desiredOffset - scrollableStartOffset).toFloat(),
            isAtDesiredOffsetAndCanBeScrolled = scrollableStartOffset == desiredOffset && canScroll,
        )
    }

    // 스크롤 실행 결정 - 실제 스크롤을 누구에게 할당할지
    private fun processOffset(
        offset: Float,
        isScrollingDown: Boolean,
        scrollData: ScrollData.VerticalScrollData,
    ): Float {
        return when {
            // 화면에 여러 아이템이 보일 때
            scrollData.visibleVerticalItemsCount > 1 -> {
                scrollColumn(
                    offset = offset,
                    isScrollingDown = isScrollingDown,
                    withLimit = scrollData.diffBetweenDesiredAndCurrentOffset,
                )
            }

            // 기준 아이템이 이상적인 위치에 있고 내부 스크롤도 가능할때
            scrollData.isAtDesiredOffsetAndCanBeScrolled -> {
                scrollInnerScrollable(
                    offset = offset,
                    scrollableState = scrollData.scrollableState,
                    isScrollingDown = isScrollingDown,
                )
            }

            // 그외
            else -> {
                val limit = if (scrollData.diffBetweenDesiredAndCurrentOffset == 0f) {
                    lazyColumnState.layoutInfo.mainAxisItemSpacing.coerceAtLeast(1).toFloat()
                } else {
                    scrollData.diffBetweenDesiredAndCurrentOffset
                }
                scrollColumn(
                    offset = offset,
                    isScrollingDown = isScrollingDown,
                    withLimit = limit,
                )
            }
        }
    }

    // 실제 스크롤 실행
    private fun scrollColumn(
        offset: Float,
        isScrollingDown: Boolean,
        withLimit: Float = Float.MAX_VALUE,
    ): Float {
        val toConsume = min(offset, withLimit)
        return if (isScrollingDown) {
            lazyColumnState.dispatchRawDelta(toConsume)
        } else {
            -lazyColumnState.dispatchRawDelta(-toConsume)
        }
    }

    private fun scrollInnerScrollable(
        offset: Float,
        scrollableState: ScrollableState,
        isScrollingDown: Boolean,
    ): Float {
        return if (isScrollingDown) {
            scrollableState.dispatchRawDelta(offset)
        } else {
            -scrollableState.dispatchRawDelta(-offset)
        }
    }

    private fun LazyListState.hasReachedScrollBoundary(isScrollingDown: Boolean): Boolean {
        return if (isScrollingDown) {
            val endOffset = layoutInfo.viewportSize.height - layoutInfo.beforeContentPadding
            !canScrollForward && layoutInfo.viewportEndOffset == endOffset
        } else {
            !canScrollBackward && layoutInfo.viewportStartOffset == -layoutInfo.beforeContentPadding
        }
    }

    private sealed interface ScrollData {

        data object NoVerticalScrollable : ScrollData

        data class VerticalScrollData(
            val visibleVerticalItemsCount: Int,
            val scrollableState: ScrollableState,
            val diffBetweenDesiredAndCurrentOffset: Float,
            val isAtDesiredOffsetAndCanBeScrolled: Boolean,
        ) : ScrollData
    }
}