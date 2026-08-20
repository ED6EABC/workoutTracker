package com.eelizarraras.workout.flows.routine.playRoutine.presentation.model

sealed class PlayRoutineEvent {
    data class LoadRoutine(val routineId: Long): PlayRoutineEvent()
    object StartRoutine: PlayRoutineEvent()
    object EndRoutine: PlayRoutineEvent()
    object PauseRoutine: PlayRoutineEvent()
    object ResumeRoutine: PlayRoutineEvent()
    object ShowEndRoutineConfirmation: PlayRoutineEvent()
    object SkipRest: PlayRoutineEvent()
    data class SetChecked(val workoutId: String, val setId: String, val isChecked: Boolean): PlayRoutineEvent()
    data class MoveWorkout(val fromIndex: Int, val toIndex: Int): PlayRoutineEvent()
    data class MoveWorkoutToDone(val workoutId: String): PlayRoutineEvent()
    data class MoveWorkoutToTodo(val workoutId: String): PlayRoutineEvent()
}