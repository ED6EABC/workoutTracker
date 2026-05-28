package com.eelizarraras.workout.flows.routine.presentation.model

import com.eelizarraras.workout.core.domine.model.WorkoutUnit

data class CreateRoutineUIModel(
    val name: String,
    val muscle: Array<String>,
    val workouts: MutableList<Workout>
)

data class Workout(
    val name: String,
    val sets: MutableList<WorkoutSet>
)

data class WorkoutSet(
    val weight: Double,
    val workoutUnit: WorkoutUnit,
    val reps: Int
)