package com.eelizarraras.workout.flows.routine.seeRoutines.model

sealed class RoutineViewerEvent {
    data class AddOrEditRoutine(val routineId: Long? = null): RoutineViewerEvent()
    data class PlayRoutine(val routineId: Long): RoutineViewerEvent()
    data class DeleteRoutine(val routineId: Long): RoutineViewerEvent()
    object GetRoutines: RoutineViewerEvent()
}