package dev.faizal.bmritest.ui.component


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun LazyGridState.isNearBottom(buffer: Int = 4): State<Boolean> = remember(this) {
    derivedStateOf {
        val last = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
        last >= layoutInfo.totalItemsCount - buffer
    }
}

@Composable
fun LazyListState.isNearBottom(buffer: Int = 3): State<Boolean> = remember(this) {
    derivedStateOf {
        val last = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
        last >= layoutInfo.totalItemsCount - buffer
    }
}