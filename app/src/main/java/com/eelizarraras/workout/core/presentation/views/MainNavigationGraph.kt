package com.eelizarraras.workout.core.presentation.views

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.eelizarraras.workout.core.domine.model.Screen
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.routine.presentation.CreateRoutineScreen
import com.eelizarraras.workout.flows.workout.presentation.PlayWorkoutScreen

@Composable
fun MainNavigationGraph(
    viewModel: NavigationViewModel
) {
    // Get the backstack
    val backStack = viewModel.backStack

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Screen.Hub> {
                DashboardHub(viewModel)
            }
            entry<Screen.AddRoutine> {
                CreateRoutineScreen()
            }
            entry<Screen.PlayWorkOut> {
                PlayWorkoutScreen()
            }
        }
    )

}