package com.eelizarraras.workout.flows.dashboard.presentation.model

import com.eelizarraras.workout.core.presentation.model.RoutineModel

data class DashboardState(
    val lastRoutineDone: RoutineModel? = null,
    val topFiveRoutines: List<RoutineModel> = listOf()
)