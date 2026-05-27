package com.eelizarraras.workout.core.presentation.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.eelizarraras.workout.core.domine.model.Screen
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.dashboard.presentation.Dashboard
import com.eelizarraras.workout.flows.progress.presentation.ProgressScreen
import com.eelizarraras.workout.flows.routine.presentation.Routine
import com.eelizarraras.workout.flows.workout.presentation.PlayWorkoutScreen

@Composable
fun NavigationGraph(
    viewModel: NavigationViewModel,
    paddingValues: PaddingValues
) {
    // Get the backstack
    val backStack = viewModel.backStack

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Screen.Dashboard> {
                Dashboard(paddingValues = paddingValues)
            }
            entry<Screen.Routine> {
                Routine(paddingValues = paddingValues)
            }
            entry<Screen.Workout> {
                ProgressScreen(paddingValues = paddingValues)
            }
            entry<Screen.PlayWorkOut> {
                PlayWorkoutScreen()
            }
        }
    )
}