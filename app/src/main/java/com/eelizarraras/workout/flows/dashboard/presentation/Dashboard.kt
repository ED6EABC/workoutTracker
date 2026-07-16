package com.eelizarraras.workout.flows.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.presentation.components.SectionHeader
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.core.presentation.views.componets.LoadingView
import com.eelizarraras.workout.flows.dashboard.presentation.components.GreetingsCard
import com.eelizarraras.workout.flows.dashboard.presentation.components.LastWorkoutCard
import com.eelizarraras.workout.flows.dashboard.presentation.components.WorkoutCard
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEffect
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEvent
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardState
import com.eelizarraras.workout.flows.dashboard.presentation.viewModel.DashboardViewModel
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.platform.LocalResources

@Composable
fun Dashboard(
    viewModel: DashboardViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }
    val resources = LocalResources.current

    LoadingView(showLoading) {
        Content(
            modifier = Modifier.padding(paddingValues),
            dashboardUiModel = state,
            onRoutinePlay = { routine ->
                //navigationViewModel.onNavigate(Screen.PlayWorkOut(routine))
            }
        )
    }

    LaunchedEffect(Unit) {

        val compliments = resources.getStringArray(R.array.compliments).toList()
        viewModel.onEvent(DashboardEvent.LoadCompliment(compliments))

        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is DashboardEffect.ShowLoading -> {
                    showLoading = effect.isLoading
                }
            }
        }
    }

}

@Preview
@Composable
private fun DashboardPreview() {
    WorkoutTrackerTheme {
        Content(
            modifier = Modifier,
            dashboardUiModel = DashboardState(
                lastRoutineDone = RoutineModel(
                    id = 0L,
                    name = "Full Body A",
                    workouts = "5",
                    durationInMinutes = "45",
                    weekDayName = "LUN"
                ),
                topFiveRoutines = listOf(
                    RoutineModel(
                        id = 1L,
                        name = "Cardio HIIT",
                        workouts = "6",
                        durationInMinutes = "50",
                        weekDayName = "MAR"
                    ),
                    RoutineModel(
                        id = 2L,
                        name = "Hipertrofia Piernas",
                        workouts = "5",
                        durationInMinutes = "60",
                        weekDayName = "MIER"
                    ),
                    RoutineModel(
                        id = 3L,
                        name = "Empuje (Push)",
                        workouts = "4",
                        durationInMinutes = "30",
                        weekDayName = "JUE"
                    )
                )
            ),
            onRoutinePlay = { routine -> }
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    dashboardUiModel: DashboardState,
    onRoutinePlay: (RoutineModel) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            GreetingsCard(dashboardUiModel.compliment)
        }

        dashboardUiModel.lastRoutineDone?.let {
            item {
                SectionHeader(title = stringResource(R.string.last_workout))
                LastWorkoutCard(dashboardUiModel.lastRoutineDone)
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionHeader(title = stringResource(R.string.my_workouts))
                Text(
                    text = stringResource(R.string.see_all),
                    style = MaterialTheme.typography.labelLarge,
                    color = TealAccent,
                    modifier = Modifier.clickable { /* Navegar a lista completa */ }
                )
            }
        }
        items(
            items = dashboardUiModel.topFiveRoutines,
            key = { it.id }
        ) { routine ->
            WorkoutCard(
                title = routine.name,
                exercisesCount = routine.workouts,
                durationMinutes = routine.durationInMinutes,
                onPlayClick = { onRoutinePlay(routine) }
            )
        }
    }
}