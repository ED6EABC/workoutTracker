package com.eelizarraras.workout.flows.routine.playRoutine.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEffect
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.RoutineDetailState
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.Workout
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.WorkoutSetWithCheck
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.components.ActiveExerciseCard
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.components.TimerComponent
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel.PlayRoutineViewModel
import com.eelizarraras.workout.core.presentation.components.ReorderableItem
import com.eelizarraras.workout.core.presentation.components.WarningCard
import com.eelizarraras.workout.core.presentation.components.reorderable
import com.eelizarraras.workout.core.presentation.components.rememberReorderableLazyListState
import com.eelizarraras.workout.core.presentation.model.WarningCardModel
import com.eelizarraras.workout.core.presentation.views.componets.LoadingView
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.compose.koinViewModel
import com.eelizarraras.workout.R

@Composable
fun PlayWorkoutScreen(
    viewModel: PlayRoutineViewModel = koinViewModel(),
    routineId: Long
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }
    var showWarningCard by remember { mutableStateOf(false) }

    LaunchedEffect(routineId) {
        viewModel.onEvent(PlayRoutineEvent.LoadRoutine(routineId))
    }

    LoadingView(showLoading) {
        Content(
            state = state,
            modifier = Modifier,
            onEvent = viewModel::onEvent
        )
    }

    BackHandler(state.isStarted) {
        viewModel.onEvent(PlayRoutineEvent.ShowEndRoutineConfirmation)
    }

    if(showWarningCard) {
        WarningCard(
            model = WarningCardModel(
                tittle = stringResource(R.string.play_routine_alert_title),
                support =  stringResource(R.string.play_routine_alert_description),
                confirmButtonLabel = stringResource(R.string.accept_label),
                dismissButtonLabel = stringResource(R.string.cancel_label),
                onConfirm = {
                    viewModel.onEvent(PlayRoutineEvent.EndRoutine)
                    showWarningCard = false
                },
                onDismiss = {
                    viewModel.onEvent(PlayRoutineEvent.ResumeRoutine)
                    showWarningCard = false
                }
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PlayRoutineEffect.ShowLoading -> {
                    showLoading = effect.isLoading
                }
                PlayRoutineEffect.ShowConfirmationDialog -> {
                    showWarningCard = true
                    viewModel.onEvent(PlayRoutineEvent.PauseRoutine)
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
            state = RoutineDetailState(
                timer = "00:00:00",
                isPaused = false,
                workouts = listOf(
                    Workout(
                        id = "1",
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
    state: RoutineDetailState,
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
            TimerComponent(
                state = state,
                onEvent = onEvent
            )

            Spacer(modifier = Modifier.height(32.dp))

            val reorderState = rememberReorderableLazyListState(onMove = { from, to ->
                onEvent(PlayRoutineEvent.MoveWorkout(from, to))
            })

            LazyColumn(
                state = reorderState.lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .reorderable(reorderState)
            ) {

                items(items = state.workouts, key = { it.id }) { workout ->
                    ReorderableItem(state = reorderState, key = workout.id) { isDragging ->
                        ActiveExerciseCard(
                            workoutId = workout.id,
                            name = workout.name,
                            setsInfo = workout.setsTotal,
                            sets = workout.sets,
                            onEvent = onEvent,
                            modifier = Modifier.graphicsLayer {
                                val scale = if (isDragging) 1.05f else 1.0f
                                scaleX = scale
                                scaleY = scale
                                alpha = if (isDragging) 0.8f else 1.0f
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}