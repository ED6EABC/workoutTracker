package com.eelizarraras.workout.flows.dashboard.presentation.model

import kotlinx.serialization.Serializable

data class DashboardUIModel(
    val compliments: Array<String>,
    val lastRoutineDone: LastRoutineDone,
    val topFiveRoutines: List<RoutineModel>
)
data class LastRoutineDone(
    val name: String,
    val weekDayName: String,
    val duration: Int
)

@Serializable
data class RoutineModel(
    val name: String,
    val workouts: Int,
    val duration: Int
)
