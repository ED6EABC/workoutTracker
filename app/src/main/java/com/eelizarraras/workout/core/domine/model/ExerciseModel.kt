package com.eelizarraras.workout.core.domine.model

data class ExerciseModel(
    val id: Long,
    val name: String,
    val note: String? = null,
    val isActive: Boolean = true
)