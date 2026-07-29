package com.eelizarraras.workout.flows.routine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp

@Composable
internal fun SwiperToDelete(
    onDeleted: () -> Unit,
    content: @Composable () -> Unit
) {

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        SwipeToDismissBoxValue.Settled,
        SwipeToDismissBoxDefaults.positionalThreshold
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromStartToEnd = false,
        onDismiss = { onDeleted() },
        backgroundContent = {

            val target = swipeToDismissBoxState.targetValue
            val progress = swipeToDismissBoxState.progress

            when(swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> {

                    val animatedProgress = when(target) {
                        SwipeToDismissBoxValue.EndToStart -> progress
                        SwipeToDismissBoxValue.Settled -> 1f - progress
                        else -> 0f
                    }

                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Swipe to delete",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                lerp(
                                    Color(0xFF121212),
                                    Color.Red,
                                    animatedProgress
                                )
                            )
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }

                else -> {}
            }
        }
    ) { content() }
}