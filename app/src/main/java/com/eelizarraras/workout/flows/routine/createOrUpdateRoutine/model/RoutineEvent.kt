package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model

import com.eelizarraras.workout.core.domine.model.WorkoutUnit

sealed class RoutineEvent {
    data object AddWorkout: RoutineEvent()
    data class Save(val routine: CreateRoutineState): RoutineEvent()
    data class SetName(val name: String): RoutineEvent()
    data class AddSet(val workoutId: String): RoutineEvent()
    data class DeleteSet(val workoutId: String, val setId: String): RoutineEvent()
    data class UpdateSet(
        val workoutId: String,
        val workoutSetId: String,
        val weight: String? = null,
        val unit: WorkoutUnit? = null,
        val reps: String? = null
    ): RoutineEvent()
    data class DeleteWorkout(val workoutId: String): RoutineEvent()
    data class SetWorkoutName(val workoutId: String, val name: String): RoutineEvent()
    data class SetRoutineRestTime(val restTime: String): RoutineEvent()
    data class SetWorkoutRestTime(val workoutId: String, val restTime: String): RoutineEvent()
    data class ResetToInitialState(val routineId: Long? = null): RoutineEvent()
    data class ShowConfirmation(val isNavigationBack: Boolean): RoutineEvent()
    data class OnRestSwitchChange(val isCheck: Boolean): RoutineEvent()
    object ValidateFields: RoutineEvent()
}