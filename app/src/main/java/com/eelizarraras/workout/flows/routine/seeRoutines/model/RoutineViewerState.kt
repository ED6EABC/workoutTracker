package com.eelizarraras.workout.flows.routine.seeRoutines.model

import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.presentation.model.RoutineModel

data class RoutineViewerState(
    val keepWorkingMessage: Int = R.string.keep_working,
    val routines: List<RoutineModel> = listOf()
)