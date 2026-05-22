package com.eelizarraras.workout.core.domine.model

data class WorkoutModel(
    val name: String,
    val description: String? = null,
    val note: String? = null
)