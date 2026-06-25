package com.eelizarraras.workout.core.domine.model

data class WorkoutModel(
    val id: Long,
    val name: String,
    val note: String? = null
)