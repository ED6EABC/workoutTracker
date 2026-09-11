package com.eelizarraras.workout.core.presentation.model

import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import java.util.UUID

data class WorkoutSet(
    val uid: String = UUID.randomUUID().toString(),
    var weight: String = "",
    var isWeightError: Boolean = false,
    val workoutUnit: WorkoutUnit = WorkoutUnit.Kg,
    val reps: String = "",
    var isRepsError: Boolean = false
)

data class WorkoutSetToUpdate(
    val workoutId: String = "",
    val setId: String = "",
    var weight: String = "",
    val workoutUnit: WorkoutUnit? = null,
    val reps: String = ""
)