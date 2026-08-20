package com.eelizarraras.workout.flows.dashboard.presentation.model

import com.eelizarraras.workout.core.presentation.model.RoutineModel

data class DashboardState(
    val compliment: String = "",
    val lastRoutines: List<RoutineModel> = listOf()
)