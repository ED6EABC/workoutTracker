package com.eelizarraras.workout.flows.routine.playRoutine.presentation.model

import com.eelizarraras.workout.core.presentation.model.WorkoutSet

data class PlayRoutineState(
    val timer: String = "00:00:00",
    val isPaused: Boolean = false,
    val isStarted: Boolean = false,
    val routineName: String = "",
    val routineId: Long = 0L,
    val workouts: List<Workout> = listOf(),
    val workoutsTotal: Int = 0
)

data class Workout(
    val id: String,
    val name: String,
    val sets: List<WorkoutSetWithCheck>,
    val setsTotal: String
)

data class WorkoutSetWithCheck(
    val workoutSet: WorkoutSet,
    val isChecked: Boolean
)
