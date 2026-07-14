package com.eelizarraras.workout.core.domine.model

data class RoutineExerciseModel(
    val id: Long,
    val routineId: Long,
    val exerciseId: Long,
    val sortOrder: Int
)