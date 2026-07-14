package com.eelizarraras.workout.flows.routine.seeRoutines.model

import com.eelizarraras.workout.core.presentation.model.Screen

sealed class RoutineViewerEvent {
    data class AddRoutine(val screen: Screen): RoutineViewerEvent()
    data class PlayRoutine(val screen: Screen): RoutineViewerEvent()
    data class DeleteRoutine(val routineId: Long): RoutineViewerEvent()
    object GetRoutines: RoutineViewerEvent()
}