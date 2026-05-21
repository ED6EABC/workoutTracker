package com.eelizarraras.workout.core.data.model

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Screen: NavKey {
    @Serializable
    data object Dashboard : Screen()
    @Serializable
    data object Routine : Screen()
    @Serializable
    data object Workout : Screen()
}