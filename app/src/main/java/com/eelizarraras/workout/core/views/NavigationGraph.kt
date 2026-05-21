package com.eelizarraras.workout.core.views

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.eelizarraras.workout.core.data.model.Screen
import com.eelizarraras.workout.core.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.dashboard.presentation.Dashboard
import com.eelizarraras.workout.flows.routine.presentation.Routine
import com.eelizarraras.workout.flows.workout.presentation.Workout
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    viewModel: NavigationViewModel = koinViewModel()
) {
    // Get the backstack
    val backStack = viewModel.backStack

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Screen.Dashboard> {
                Dashboard()
            }
            entry<Screen.Routine> {
                Routine()
            }
            entry<Screen.Workout> {
                Workout()
            }
        }
    )
}