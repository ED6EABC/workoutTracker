package com.eelizarraras.workout.flows.dashboard.presentation.model

import com.eelizarraras.workout.core.presentation.model.RoutineModel

data class DashboardState(
    val lastRoutineDone: LastRoutineDone? = null,
    val topFiveRoutines: List<RoutineModel> = listOf()
)
data class LastRoutineDone(
    val name: String,
    val weekDayName: String,
    val duration: String
)
