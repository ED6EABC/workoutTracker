package com.eelizarraras.workout.core.domine.model

import androidx.navigation3.runtime.NavKey
import com.eelizarraras.workout.R
import com.eelizarraras.workout.flows.dashboard.presentation.model.RoutineModel
import kotlinx.serialization.Serializable

sealed class Screen: NavKey {

    var topBarLabel: Int = R.string.dashboard_title

    @Serializable
    data object Dashboard : Screen() {
        init {
            this.topBarLabel = R.string.dashboard_title
        }
    }
    @Serializable
    data object Routine : Screen() {
        init {
            this.topBarLabel = R.string.my_workouts
        }
    }
    @Serializable
    data object Workout : Screen() {
        init {
            this.topBarLabel = R.string.progress
        }
    }

    @Serializable
    data class PlayWorkOut(
        val routine: RoutineModel
    ): Screen() {

    }
}