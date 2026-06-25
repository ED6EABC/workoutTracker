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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.presentation.components.SectionHeader
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.dashboard.presentation.components.GreetingsCard
import com.eelizarraras.workout.flows.dashboard.presentation.components.LastWorkoutCard
import com.eelizarraras.workout.flows.dashboard.presentation.components.WorkoutCard
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardUIModel
import com.eelizarraras.workout.flows.dashboard.presentation.model.LastRoutineDone
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun Dashboard(
    navigationViewModel: NavigationViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    //TODO Retrieve this from a viewModel
    val temporal = DashboardUIModel(
        compliments = stringArrayResource(R.array.compliments),
        lastRoutineDone = LastRoutineDone(
            name = "Full Body A",
            weekDayName = "LUN",
            duration = 45
        ),
        topFiveRoutines = listOf(
            RoutineModel(id = 1L, name = "Cardio HIIT", workouts = 6, durationInMinutes = "50"),
            RoutineModel(id = 2L, name = "Hipertrofia Piernas", workouts = 5, durationInMinutes = "60"),
            RoutineModel(id = 3L, name = "Empuje (Push)", workouts = 4, durationInMinutes = "30")
        )
    )

    Content(
        modifier = Modifier.padding(paddingValues),
        dashboardUiModel = temporal,
        onRoutinePlay = { routine ->
            //navigationViewModel.onNavigate(Screen.PlayWorkOut(routine))
        }
    )
}

@Preview
@Composable
private fun DashboardPreview() {
    WorkoutTrackerTheme {
        Content(
            modifier = Modifier,
            dashboardUiModel = DashboardUIModel(
                compliments = stringArrayResource(R.array.compliments),
                lastRoutineDone = LastRoutineDone(
                    name = "Full Body A",
                    weekDayName = "LUN",
                    duration = 45
                ),
                topFiveRoutines = listOf(
                    RoutineModel(id = 1L, name = "Cardio HIIT", workouts = 6, durationInMinutes = "50"),
                    RoutineModel(id = 2L, name = "Hipertrofia Piernas", workouts = 5, durationInMinutes = "60"),
                    RoutineModel(id = 3L, name = "Empuje (Push)", workouts = 4, durationInMinutes = "30")
                )
            ),
            onRoutinePlay = { routine -> }
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    dashboardUiModel: DashboardUIModel,
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
            GreetingsCard()
        }
        item {
            SectionHeader(title = stringResource(R.string.last_workout))
            LastWorkoutCard()
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
            key = { it.name + it.workouts + it.durationInMinutes }
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