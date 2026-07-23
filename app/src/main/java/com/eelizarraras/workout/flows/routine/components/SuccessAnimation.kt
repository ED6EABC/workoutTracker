package com.eelizarraras.workout.flows.routine.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Stable
@Composable
fun SuccessAnimation(
    startAnimation: Boolean,
    onCompleted: () -> Unit
) {

    val iconScale by animateFloatAsState(
        targetValue = if (startAnimation) 1.5f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "IconScale"
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {

            val maxSize = if(maxWidth > maxHeight) maxWidth else maxHeight

            val circleScale by animateFloatAsState(
                targetValue = if (startAnimation) maxSize.value else 0f,
                animationSpec = tween(durationMillis = 800),
                label = "CircleScale",
                finishedListener = {
                    onCompleted()
                }
            )

            Box(
                modifier = Modifier
                    .size(1.dp)
                    .graphicsLayer {
                        scaleX = circleScale
                        scaleY = circleScale
                    }
                    .background(
                        Color.Green,
                        shape = CircleShape
                    )
            )

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer {
                        scaleX = iconScale
                        scaleY = iconScale
                    },
                tint = Color.White
            )

        }
    )
}

@Preview
@Composable
private fun SuccessAnimationPreview() {
    SuccessAnimation(true) {}
}