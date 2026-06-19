package com.eelizarraras.workout.flows.routine.createRoutine.model

import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import java.util.UUID

data class CreateRoutineState(
    val name: String = "",
    val muscle: List<String> = emptyList(),
    val workouts: List<Workout> = emptyList()
)

data class Workout(
    val uid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val sets: List<WorkoutSet> = listOf(WorkoutSet())
)
