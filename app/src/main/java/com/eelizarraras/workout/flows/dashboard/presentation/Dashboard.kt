package com.eelizarraras.workout.flows.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
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
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEffect
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEvent
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardState
import com.eelizarraras.workout.flows.dashboard.presentation.viewModel.DashboardViewModel
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.platform.LocalResources
import com.eelizarraras.workout.ui.theme.TealAccent

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
            dashboardUiModel = state
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
                lastRoutines = listOf(
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
            )
        )
    }
}

@Preview
@Composable
private fun DashboardPreviewEmpty() {
    WorkoutTrackerTheme {
        Content(
            modifier = Modifier,
            dashboardUiModel = DashboardState(
                lastRoutines = listOf()
            )
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    dashboardUiModel: DashboardState
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GreetingsCard(dashboardUiModel.compliment)
        Spacer(modifier = Modifier.height(32.dp))
        if(dashboardUiModel.lastRoutines.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                    SectionHeader(title = stringResource(R.string.last_workout))
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "History",
                        tint = TealAccent,
                        modifier = Modifier.size(30.dp)
                    )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dashboardUiModel.lastRoutines) { item ->
                    LastWorkoutCard(item)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add workout",
                    modifier = Modifier.size(50.dp),
                    tint = Color.White
                )
                Spacer(Modifier.height(32.dp))
                SectionHeader(title = stringResource(R.string.without_workouts))
            }
        }
    }
}