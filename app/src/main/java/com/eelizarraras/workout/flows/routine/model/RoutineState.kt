package com.eelizarraras.workout.flows.routine.model

import com.eelizarraras.workout.core.presentation.model.RoutineModel

data class RoutineState(
    val routines: List<RoutineModel> = listOf()
)