package com.example.mycomposetoy.core.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle

// 텍스트를 단어 단위로 쪼개서 애니메이션 만들기
class WordByWordAnimationState(
    textMeasurer: TextMeasurer,
    defaultTextStyle: TextStyle
): ChunkedTextAnimationState(textMeasurer, defaultTextStyle) {

    override fun findChunks(text: String, layoutResult: TextLayoutResult): List<TextChunk> {
        // 공백이 아닌 문자가 하나 이상 연속되는 정규식 = 띄어쓰기로 구분된 모든 단어 찾기
        val wordRegex = "\\S+".toRegex()
        return wordRegex.findAll(text).map { matchResult ->
            // 핵심 : 인덱스에서 시작하는 단어는 화면의 어느 좌표에 그릴지 판단
            val offset = layoutResult.getWordOffset(matchResult.range.start)
            val word = matchResult.value
            TextChunk(word, offset)
        }.toList()
    }

    // 해당 위치의 단어를 감싸는 사각형 정보의 왼쪽 위 좌표를 기준으로 좌표판단
    private fun TextLayoutResult.getWordOffset(wordStart: Int): Offset {
        return this.getBoundingBox(wordStart).topLeft
    }
}