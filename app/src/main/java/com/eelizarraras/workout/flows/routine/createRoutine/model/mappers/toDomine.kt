package com.eelizarraras.workout.flows.routine.createRoutine.model.mappers

import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.createRoutine.model.Workout

fun Workout.toDomine(): WorkoutModel {
    return WorkoutModel(
        id = 0L,
        name = this.name,
        note = ""
    )
}

fun WorkoutSet.toDomine(): WorkoutSetModel {
    return WorkoutSetModel(
        id = 0L,
        weight = this.weight.toDouble(),
        workoutUnit = this.workoutUnit,
        reps = this.reps.toInt()
    )
}