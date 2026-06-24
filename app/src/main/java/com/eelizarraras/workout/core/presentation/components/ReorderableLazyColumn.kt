package com.eelizarraras.workout.core.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberReorderableLazyListState(
    lazyListState: LazyListState = rememberLazyListState(),
    onMove: (Int, Int) -> Unit
): ReorderableLazyListState {
    val coroutineScope = rememberCoroutineScope()
    return remember(lazyListState, coroutineScope, onMove) {
        ReorderableLazyListState(lazyListState, onMove)
    }
}

class ReorderableLazyListState(
    val lazyListState: LazyListState,
    private val onMove: (Int, Int) -> Unit
) {
    private var draggingItemIndex by mutableStateOf<Int?>(null)
    internal var draggingItemOffset by mutableFloatStateOf(0f)

    internal val draggingItem: LazyListItemInfo?
        get() = draggingItemIndex?.let { index ->
            lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
        }

    internal fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                // Validate if the given offset is inside any of the visible item
                offset.y.toInt() in item.offset..(item.offset + item.size)
            }?.also {
                // Get the index of the element that is being dragged
                draggingItemIndex = it.index
            }
    }

    internal fun onDragInterrupted() {
        // Reset the dragging state
        draggingItemIndex = null
        draggingItemOffset = 0f
    }

    internal fun onDrag(offset: Offset) {
        // Get the movement related to the dragging item
        draggingItemOffset += offset.y

        val draggingItem = draggingItem ?: return
        // draggingItem.offset -> Is the starting pint of the element related to its parent
        // So startOffset is the actual starting position of the item
        val startOffset = draggingItem.offset + draggingItemOffset
        // endOffset is the end of the element
        val endOffset = startOffset + draggingItem.size

        val targetItem = lazyListState.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                // This is like a middle line inside each item
                val center = item.offset + item.size / 2
                // Now its validate if the items are overlapping and are different items
                val areItemsOverlapping = center in startOffset.toInt()..endOffset.toInt()
                areItemsOverlapping && draggingItem.index != item.index
            }

        if (targetItem != null) {
            // Now here we swap the items positions
            onMove(draggingItem.index, targetItem.index)
            draggingItemIndex = targetItem.index
            // Finally we reset the dragging offset.
            // Taking the difference between the targetItem.offset and the draggingItem.offset (This is the new position of the item)
            // And remove it from the draggingItemOffset
            draggingItemOffset -= (targetItem.offset - draggingItem.offset)
        }
    }
}

fun Modifier.reorderable(state: ReorderableLazyListState): Modifier = this.pointerInput(state) {
    detectDragGesturesAfterLongPress(
        onDragStart = { state.onDragStart(it) },
        onDragEnd = { state.onDragInterrupted() },
        onDragCancel = { state.onDragInterrupted() },
        onDrag = { change, dragAmount ->
            change.consume()
            state.onDrag(dragAmount)
        }
    )
}

@Composable
fun LazyItemScope.ReorderableItem(
    state: ReorderableLazyListState,
    key: Any,
    modifier: Modifier = Modifier,
    content: @Composable (isDragging: Boolean) -> Unit
) {
    val isDragging = state.draggingItem?.key == key
    val offsetAnimatable = remember { Animatable(0f) }

    LaunchedEffect(isDragging) {
        if (!isDragging) {
            offsetAnimatable.animateTo(0f, spring(stiffness = Spring.StiffnessLow))
        }
    }

    Box(
        modifier = modifier
            .zIndex(if (isDragging) 1f else 0f)
            .graphicsLayer {
                translationY = if (isDragging) state.draggingItemOffset else offsetAnimatable.value
            }
    ) {
        content(isDragging)
    }
}
