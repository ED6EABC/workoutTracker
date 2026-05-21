package com.eelizarraras.workout.core.domine.model

data class Workout(
    val name: String,
    val description: String? = null,
    val note: String? = null
)