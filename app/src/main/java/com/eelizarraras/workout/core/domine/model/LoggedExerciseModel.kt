package com.eelizarraras.workout.core.domine.model

data class LoggedExerciseModel(
    val id: Long = 0L,
    val exerciseId: Long,
    val sortOrder: Int,
    val sets: List<LoggedSetModel>
)
