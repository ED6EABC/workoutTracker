package com.eelizarraras.workout.core.domine.model

data class WorkoutSetModel(
    val id: Long,
    val weight: Double,
    val workoutUnit: WorkoutUnit,
    val reps: Int
)