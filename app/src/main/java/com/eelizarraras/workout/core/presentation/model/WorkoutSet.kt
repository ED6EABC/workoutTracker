package com.eelizarraras.workout.core.presentation.model

import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import java.util.UUID

data class WorkoutSet(
    val uid: String = UUID.randomUUID().toString(),
    var weight: String = "",
    val workoutUnit: WorkoutUnit = WorkoutUnit.Kg,
    val reps: String = ""
)