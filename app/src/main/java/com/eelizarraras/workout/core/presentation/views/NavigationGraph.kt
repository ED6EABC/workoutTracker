package com.eelizarraras.workout.core.presentation.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.eelizarraras.workout.core.domine.model.BottomBarScreen
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.dashboard.presentation.Dashboard
import com.eelizarraras.workout.flows.progress.presentation.ProgressScreen
import com.eelizarraras.workout.flows.routine.presentation.Routine

@Composable
fun NavigationGraph(
    viewModel: NavigationViewModel,
    paddingValues: PaddingValues
) {
    // Get the backstack
    val backStack = viewModel.bottomBackstack

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<BottomBarScreen.Dashboard> {
                Dashboard(paddingValues = paddingValues)
            }
            entry<BottomBarScreen.Routine> {
                Routine(paddingValues = paddingValues)
            }
            entry<BottomBarScreen.Workout> {
                ProgressScreen(paddingValues = paddingValues)
            }

        }
    )
}