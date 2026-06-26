package com.eelizarraras.workout.core.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class RoutineModel(
    val id: Long = 0L,
    val name: String,
    val workouts: String,
    val durationInMinutes: String,
    val weekDayName: String
)