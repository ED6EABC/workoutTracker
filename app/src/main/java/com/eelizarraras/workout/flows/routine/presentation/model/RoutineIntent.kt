package com.eelizarraras.workout.flows.routine.presentation.model

import com.eelizarraras.workout.core.domine.model.WorkoutUnit

sealed class RoutineIntent {
    data object AddWorkout: RoutineIntent()
    data class Save(val routine: CreateRoutineState): RoutineIntent()
    data class SetName(val name: String): RoutineIntent()
    data class AddSet(val workoutId: String): RoutineIntent()
    data class UpdateSet(
        val workoutId: String,
        val workoutSetId: String,
        val weight: String? = null,
        val unit: WorkoutUnit? = null,
        val reps: String? = null
    ): RoutineIntent()
    data class DeleteWorkout(val workoutId: String): RoutineIntent()
    data class SetWorkoutName(val workoutId: String, val name: String): RoutineIntent()
}