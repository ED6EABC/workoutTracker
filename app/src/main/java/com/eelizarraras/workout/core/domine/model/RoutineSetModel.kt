package com.eelizarraras.workout.core.domine.model

data class RoutineSetModel(
    val id: Long,
    val routineExerciseId: Long,
    val setOrder: Int,
    val weight: Double,
    val workoutUnit: WorkoutUnit,
    val reps: Int,
    val isActive: Boolean = true
)