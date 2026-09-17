package com.eelizarraras.workout.core.domine.model

data class LoggedSetModel(
    val id: Long = 0L,
    val setOrder: Int,
    val reps: Int,
    val weight: Double,
    val unit: WorkoutUnit,
    val isComplete: Boolean
)
