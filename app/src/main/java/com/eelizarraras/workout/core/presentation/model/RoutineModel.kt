package com.eelizarraras.workout.core.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class RoutineModel(
    val name: String,
    val workouts: Int,
    val durationInMinutes: String
)