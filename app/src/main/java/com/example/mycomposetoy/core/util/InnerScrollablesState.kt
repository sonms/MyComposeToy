package com.example.mycomposetoy.core.util

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.grid.LazyGridState

class InnerScrollablesState {
    private val _gridStates: MutableMap<Int, ScrollableState> = mutableMapOf()
    fun getState(index: Int): ScrollableState? = _gridStates[index]

    fun getOrCreateStateForGrid(carouselIndex: Int): LazyGridState {
        val scrollableState = _gridStates[carouselIndex]
        return if (scrollableState != null) {
            scrollableState as LazyGridState
        } else {
            LazyGridState().also {
                _gridStates[carouselIndex] = it
            }
        }
    }

    fun clearState() {
        _gridStates.clear()
    }
}