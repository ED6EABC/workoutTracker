package com.eelizarraras.workout.flows.routine.playRoutine.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineState
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun TimerComponent(
    state: PlayRoutineState,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Content(
        timer = state.timer,
        isPaused = state.isPaused,
        isStarted = state.isStarted,
        onEvent = onEvent
    )
}

@Preview
@Composable
private fun TimerComponentPreview() {
    WorkoutTrackerTheme {
        Content(
            timer = "00:00:00",
            isPaused = false,
            isStarted = false,
            onEvent = {}
        )
    }
}

@Composable
private fun Content(
    timer: String,
    isPaused: Boolean,
    isStarted: Boolean,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timer,
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 64.sp,
                color = Color(0xFFC4D1FF)
            )
        )
        Text(
            text = stringResource(R.string.current_duration),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.6f),
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons

        AnimatedVisibility(isStarted) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val pauseAndResumeLabel: Int
                val pauseAndResumeEvent: PlayRoutineEvent

                if (isPaused) {
                    pauseAndResumeLabel = R.string.resume
                    pauseAndResumeEvent = PlayRoutineEvent.ResumeRoutine
                } else {
                    pauseAndResumeLabel = R.string.pause
                    pauseAndResumeEvent = PlayRoutineEvent.PauseRoutine
                }

                OutlinedButton(
                    onClick = { onEvent(pauseAndResumeEvent) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text(
                        text = stringResource(pauseAndResumeLabel),
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { onEvent(PlayRoutineEvent.ShowEndRoutineConfirmation) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B0000),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.finish),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        AnimatedVisibility(!isStarted) {
            Button(
                onClick = { onEvent(PlayRoutineEvent.StartRoutine) },
                modifier = Modifier.height(56.dp).fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.start),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}