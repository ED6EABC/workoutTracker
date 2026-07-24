package com.eelizarraras.workout.core.domine.model

data class RoutineDetailModel(
    val id: Long,
    val name: String,
    val restTimeInSeconds: Int? = null,
    val workouts: List<WorkoutWithSetsModel>
)

data class WorkoutWithSetsModel(
    val id: Long,
    val name: String,
    val note: String?,
    val restTimeInSeconds: Int? = null,
    val sets: List<RoutineSetModel>
)