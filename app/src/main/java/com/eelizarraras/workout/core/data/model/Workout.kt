package com.eelizarraras.workout.core.data.model

data class Workout(
    val name: String,
    val description: String? = null,
    val note: String? = null
)