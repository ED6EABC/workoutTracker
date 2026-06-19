package com.eelizarraras.workout.flows.routine.playRoutine.model

sealed class PlayRoutineEvent {
    data class LoadRoutine(val routineId: Long): PlayRoutineEvent()
    object StartRoutine: PlayRoutineEvent()
    object EndRoutine: PlayRoutineEvent()
    object PauseRoutine: PlayRoutineEvent()
    object ResumeRoutine: PlayRoutineEvent()
    data class SetChecked(val uid: String, val isChecked: Boolean): PlayRoutineEvent()
}