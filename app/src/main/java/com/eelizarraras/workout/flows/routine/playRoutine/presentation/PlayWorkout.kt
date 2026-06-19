package com.eelizarraras.workout.flows.routine.playRoutine.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eelizarraras.workout.R
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEffect
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineState
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel.PlayRoutineViewModel
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.TealAccent
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
                workouts = listOf(),
                workoutsTotal = 0
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
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Timer Section
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

            // Exercise List
            ActiveExerciseCard(
                name = "Barbell Squats",
                setsInfo = "4 Sets • 8-10 Reps",
                isExpanded = true,
                onEvent = onEvent
            )

            Spacer(modifier = Modifier.height(16.dp))

            ActiveExerciseCard(
                name = "Dumbbell Bench Press",
                setsInfo = "3 Sets • 12 Reps",
                isExpanded = false,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun ActiveExerciseCard(
    name: String,
    setsInfo: String,
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreyCardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFC4D1FF),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color(0xFFC4D1FF)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.6f)
                )
            }
            Text(
                text = setsInfo,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.6f)
            )

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Header Table
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        stringResource(R.string.set),
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                    Text(
                        "kg",
                        textAlign = TextAlign.Center,
                        color = TealAccent,
                        fontSize = 12.sp
                    )
                    Text(
                        stringResource(R.string.reps),
                        textAlign = TextAlign.Center,
                        color = TealAccent,
                        fontSize = 12.sp
                    )
                    Icon(
                        Icons.Default.DoneAll,
                        null,
                        tint = Color.White.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ActiveSetRow(
                    setNumber = 1,
                    weight = "80",
                    reps = "10",
                    isChecked = true,
                    onCheckedChange = {
                        onEvent(PlayRoutineEvent.SetChecked("1", it))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                ActiveSetRow(
                    setNumber = 2,
                    weight = "80",
                    reps = "10",
                    isChecked = true,
                    onCheckedChange = {
                        onEvent(PlayRoutineEvent.SetChecked("2", it))
                    }
                )
            }
        }
    }
}

@Composable
private fun ActiveSetRow(
    setNumber: Int,
    weight: String,
    reps: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isChecked) Color.White.copy(alpha = 0.05f) else Color.Transparent)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setNumber.toString(),
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = weight, color = Color.White.copy(alpha = 0.4f))
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = reps, color = Color.White.copy(alpha = 0.4f))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) TealAccent else Color.Transparent)
                .border(
                    1.dp,
                    if (isChecked) Color.Transparent else Color.White.copy(alpha = 0.2f),
                    RoundedCornerShape(8.dp)
                )
                .clickable { onCheckedChange(isChecked) }
        )
    }
}
