package com.eelizarraras.workout.core.data.model.entity.view

/**
 * Data model for routine overview including workout count and latest record information.
 */
data class RoutineOverviewEntity(
    val uid: Long,
    val name: String,
    val workoutCount: Int,
    val duration: Long? = null,
    val date: Long? = null
)
