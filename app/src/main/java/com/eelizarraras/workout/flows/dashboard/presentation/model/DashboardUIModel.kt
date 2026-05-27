package com.eelizarraras.workout.flows.dashboard.presentation.model

import com.eelizarraras.workout.core.presentation.model.RoutineModel

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
