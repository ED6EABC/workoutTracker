package com.eelizarraras.workout.core.domine.model

import androidx.navigation3.runtime.NavKey
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import kotlinx.serialization.Serializable

sealed class Screen: NavKey {
    @Serializable
    data object Hub : Screen() {
    }

    @Serializable
    data class PlayWorkOut(
        val routine: RoutineModel
    ): Screen() {

    }
    @Serializable
    data object AddRoutine: Screen()
}

sealed class BottomBarScreen: NavKey {
    var topBarLabel: Int = R.string.dashboard_title

    @Serializable
    data object Dashboard : BottomBarScreen() {
        init {
            this.topBarLabel = R.string.dashboard_title
        }
    }
    @Serializable
    data object Routine : BottomBarScreen() {
        init {
            this.topBarLabel = R.string.my_workouts
        }
    }
    @Serializable
    data object Workout : BottomBarScreen() {
        init {
            this.topBarLabel = R.string.progress
        }
    }
}