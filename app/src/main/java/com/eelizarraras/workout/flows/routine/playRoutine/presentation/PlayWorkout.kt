package com.eelizarraras.workout.flows.routine.playRoutine.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEffect
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineState
import com.eelizarraras.workout.flows.routine.playRoutine.model.Workout
import com.eelizarraras.workout.flows.routine.playRoutine.model.WorkoutSetWithCheck
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.components.ActiveExerciseCard
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel.PlayRoutineViewModel
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayWorkoutScreen(
    viewModel: PlayRoutineViewModel = koinViewModel(),
    routineId: Long
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(routineId) {
        viewModel.onEvent(PlayRoutineEvent.LoadRoutine(routineId))
    }

    Content(
        state = state,
        modifier = Modifier,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                PlayRoutineEffect.ShowLoading -> {
                    // Show loading
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun PlayWorkoutPreview() {
    WorkoutTrackerTheme {
        Content(
            state = PlayRoutineState(
                timer = "00:00:00",
                isPaused = false,
                workouts = listOf(
                    Workout(
                        id = 1L,
                        name = "Tren superior",
                        sets = listOf(
                            WorkoutSetWithCheck(
                                workoutSet = WorkoutSet(
                                    uid = "1",
                                    weight = "10.0",
                                    workoutUnit = WorkoutUnit.Lbs,
                                    reps = "10"
                                ),
                                isChecked = false
                            ),
                            WorkoutSetWithCheck(
                                workoutSet = WorkoutSet(
                                    uid = "1",
                                    weight = "10.0",
                                    workoutUnit = WorkoutUnit.Lbs,
                                    reps = "10"
                                ),
                                isChecked = false
                            )
                        ),
                        setsTotal = "2"
                    )
                ),
                workoutsTotal = 4
            ),
            modifier = Modifier,
            onEvent = { }
        )
    }
}

@Composable
private fun Content(
    state: PlayRoutineState,
    modifier: Modifier = Modifier,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.timer,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val label: String
                val event: PlayRoutineEvent

                if (state.isPaused) {
                    label = stringResource(R.string.resume)
                    event = PlayRoutineEvent.ResumeRoutine
                } else {
                    label = stringResource(R.string.pause)
                    event = PlayRoutineEvent.PauseRoutine
                }

                OutlinedButton(
                    onClick = { onEvent(event) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text(
                        text = label,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { onEvent(PlayRoutineEvent.EndRoutine) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B0000), // Rojo oscuro
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.finish),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                items(items = state.workouts, key = { it.id }) {
                    ActiveExerciseCard(
                        name = it.name,
                        setsInfo = it.setsTotal,
                        sets = it.sets,
                        onEvent = onEvent
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}