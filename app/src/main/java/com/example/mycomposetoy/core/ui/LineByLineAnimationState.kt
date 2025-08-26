package com.example.mycomposetoy.core.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle

// 텍스트를 줄 단위로 쪼개서 애니메이션 생성
class LineByLineAnimationState(
    textMeasurer: TextMeasurer,
    defaultTextStyle: TextStyle
): ChunkedTextAnimationState(textMeasurer, defaultTextStyle) {

    override fun findChunks(text: String, layoutResult: TextLayoutResult): List<TextChunk> {
        val lines = mutableListOf<TextChunk>()

        // 줄 개수를 알아내고 줄 순회
        for (line in 0 until layoutResult.lineCount) {
            // 글자 인덱스 판단
            val start = layoutResult.getLineStart(line)
            val end = layoutResult.getLineEnd(line)
            val lineText = text.substring(start, end)
            // 현재 줄을 그려야할 좌표 판단 - layoutResult가 이미 정보 계산해둠
            val offset = Offset(
                x = layoutResult.getLineLeft(line),
                y = layoutResult.getLineTop(line)
            )
            lines.add(TextChunk(lineText, offset))
        }

        return lines
    }
}