package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model

import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import java.util.UUID

data class CreateRoutineState(
    val routineId: Long = 0L,
    val name: String = "",
    val isNameError: Boolean = false,
    val restTime: String = "00:00",
    val workouts: List<Workout> = emptyList(),
    val isNavigationBack: Boolean = false,
    val isUpdating: Boolean = false,
    val showAnimation: Boolean = false,
    val isRestSwitchChecked: Boolean = false
)

data class Workout(
    val uid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val isNameError: Boolean = false,
    val restTime: String = "00:00",
    val sets: List<WorkoutSet> = listOf(WorkoutSet())
)
