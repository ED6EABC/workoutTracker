package com.eelizarraras.workout.flows.routine.playRoutine.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEvent
import com.eelizarraras.workout.ui.theme.TealAccent

@Preview
@Composable
private fun RestTimerOverlayPreview() {
    RestTimerOverlay(
        restTime = "00:00:00",
        onEvent = { }
    )
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFE30000,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun RestTimerOverlayTopPreview() {
    RestTimerOverlay(
        restTime = "00:00:00",
        showOnTop = true,
        onEvent = { }
    )
}

@Composable
fun RestTimerOverlay(
    restTime: String,
    showOnTop: Boolean = false,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF121212)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .clickable(enabled = false) {}
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if(showOnTop) Arrangement.Top else Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.recovery_timer),
                    style = MaterialTheme.typography.labelLarge,
                    color = TealAccent,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = restTime,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 80.sp,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = { onEvent(PlayRoutineEvent.SkipRest) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.1f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.height(48.dp).width(160.dp)
                ) {
                    Text(
                        text = stringResource(R.string.skip_rest),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}