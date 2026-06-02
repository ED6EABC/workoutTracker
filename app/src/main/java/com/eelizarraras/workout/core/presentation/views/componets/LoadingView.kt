package com.eelizarraras.workout.core.presentation.views.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Preview
@Composable
private fun LoadingViewPreview() {
    WorkoutTrackerTheme {
        LoadingView() {}
    }
}

@Composable
fun LoadingView(
    isLoading: Boolean = false,
    content: @Composable () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = if(isLoading) 10.dp else 0.dp, edgeTreatment = BlurredEdgeTreatment.Rectangle)
        ) {
            content()
        }
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(150.dp),
                    gapSize = 16.dp
                )
            }
        }
    }
}