package com.eelizarraras.workout.flows.routine.createRoutine.model

import com.eelizarraras.workout.core.domine.model.WorkoutUnit
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

data class WorkoutSet(
    val uid: String = UUID.randomUUID().toString(),
    var weight: String = "",
    val workoutUnit: WorkoutUnit = WorkoutUnit.Kg,
    val reps: String = ""
)
