package com.example.mycomposetoy.core.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 확장 가능 형태로 텍스트를 나눌 기준을 모르니 하위 클래스에 넘김
abstract class ChunkedTextAnimationState(
    private val textMeasurer: TextMeasurer, //텍스트의 크기와 위치를 측정하는 데 사용
    val defaultTextStyle: TextStyle
) {

    var boxSize by mutableStateOf<IntSize>(IntSize.Zero)
        private set

    var textStyle by mutableStateOf<TextStyle>(defaultTextStyle)
        private set

    // loadText가 호출될 때 계산된 전체 텍스트 청크 리스트
    private var chunks = emptyList<TextChunk>()

    // 실제로 화면에 보여줄 청크들의 리스트
    // showText가 실행되면서 chunks에 있던 아이템들이 하나씩 이 리스트로 옮겨짐
    private val _chunksToDisplay = mutableStateListOf<TextChunk>()
    val chunksToDisplay: List<TextChunk> = _chunksToDisplay

    // showText 애니메이션을 실행하는 코루틴(비동기 작업)의 핸들
    // 이 Job이 있어야 애니메이션 도중에 중단시키는(cancel()) 것이 가능
    private var showTextJob: Job? = null

    suspend fun showText(delayBetweenChunksMillis: Long) = coroutineScope {
        dismissText()
        showTextJob = launch {
            for (chunk in chunks) {
                _chunksToDisplay.add(chunk)
                delay(delayBetweenChunksMillis)
            }
        }
    }

    fun loadText(
        text: String,
        style: TextStyle = this.defaultTextStyle,
        constraints: Constraints = Constraints()
    ) {
        clearState()
        val layoutResult = textMeasurer.measure(
            text = text,
            style = style,
            constraints = constraints
        )

        textStyle = style
        boxSize = layoutResult.size
        chunks = findChunks(text, layoutResult) // 호출하여 텍스트를 청크 단위로 나눈 뒤 chunks 리스트에 저장
    }

    protected abstract fun findChunks(text: String, layoutResult: TextLayoutResult): List<TextChunk>

    fun clearState() {
        dismissText()
        boxSize = IntSize.Zero
        chunks = emptyList()
    }

    fun dismissText() {
        showTextJob?.cancel()
        showTextJob = null
        _chunksToDisplay.clear()
    }
}