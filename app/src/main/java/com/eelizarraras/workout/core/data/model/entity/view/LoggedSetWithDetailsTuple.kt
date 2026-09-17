package com.eelizarraras.workout.core.data.model.entity.view

import com.eelizarraras.workout.core.domine.model.WorkoutUnit

data class LoggedSetWithDetailsTuple(
    val exerciseName: String,
    val weight: Double,
    val unit: WorkoutUnit,
    val date: Long
)
