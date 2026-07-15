package com.eelizarraras.workout.flows.routine.seeRoutines.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.presentation.model.Screen
import com.eelizarraras.workout.core.presentation.components.SectionHeader
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.core.presentation.views.componets.LoadingView
import com.eelizarraras.workout.flows.routine.components.RoutineActionCard
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerEffect
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerEvent
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerState
import com.eelizarraras.workout.flows.routine.seeRoutines.presentation.viewModel.RoutineViewerViewModel
import com.eelizarraras.workout.ui.theme.BlueCardBackground
import com.eelizarraras.workout.ui.theme.CardContentColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun Routine(
    viewModel: RoutineViewerViewModel = koinViewModel(),
    paddingValues: PaddingValues,
    onNavigate: (Screen) -> Unit
) {

    val state by viewModel.uiState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }

    LoadingView(showLoading) {
        Content(
            paddingValues = paddingValues,
            state = state,
            onEvent = viewModel::handleEvent
        )
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is RoutineViewerEffect.ShowLoading -> {
                    showLoading = effect.isLoading
                }
                is RoutineViewerEffect.OnNavigateTo -> {
                    onNavigate(effect.screen)
                }
            }
        }
    }
}

@Preview
@Composable
private fun RoutinePreview() {
    Content(
        paddingValues = PaddingValues(0.dp),
        state = RoutineViewerState(routines = listOf(
            RoutineModel(id = 1L, name = "Cardio HIIT", workouts = "6", durationInMinutes = "50", weekDayName = "LUN"),
            RoutineModel(id = 2L, name = "Hipertrofia Piernas", workouts = "5", durationInMinutes = "60", weekDayName = "MAR"),
            RoutineModel(id = 3L, name = "Empuje (Push)", workouts = "4", durationInMinutes = "30", weekDayName = "MIER")
        )),
        onEvent = {}
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    state: RoutineViewerState,
    onEvent: (RoutineViewerEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onEvent(RoutineViewerEvent.AddOrEditRoutine()) },
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                shape = RoundedCornerShape(16.dp),
                containerColor = BlueCardBackground,
                contentColor = CardContentColor,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_routine)
                    )
                },
                text = {
                    Text(
                        stringResource(R.string.new_workout),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(paddingValues)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                SectionHeader(
                    title = stringResource(R.string.workouts_saved),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.keep_working),
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                    color = CardContentColor.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
            items(
                items = state.routines,
                key = { it.id }
            ) { routine ->
                RoutineActionCard(
                    title = routine.name,
                    duration = routine.durationInMinutes,
                    exercisesCount = routine.workouts,
                    onPlayClick = { onEvent(RoutineViewerEvent.PlayRoutine(routine.id)) },
                    onMoreClick = { onEvent(RoutineViewerEvent.AddOrEditRoutine(routine.id)) },
                    onDeleted = { onEvent(RoutineViewerEvent.DeleteRoutine(routine.id)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
