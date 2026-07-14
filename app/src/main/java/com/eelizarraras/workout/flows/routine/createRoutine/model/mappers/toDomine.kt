package com.eelizarraras.workout.flows.routine.createRoutine.model.mappers

import com.eelizarraras.workout.core.domine.model.ExerciseModel
import com.eelizarraras.workout.core.domine.model.RoutineSetModel
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.createRoutine.model.Workout

fun Workout.toDomine(): ExerciseModel {
    return ExerciseModel(
        id = 0L,
        name = this.name,
        note = ""
    )
}

fun WorkoutSet.toDomine(): RoutineSetModel {
    return RoutineSetModel(
        id = 0L,
        routineExerciseId = 0L,
        setOrder = 0, // Should be set during saving
        weight = this.weight.toDouble(),
        workoutUnit = this.workoutUnit,
        reps = this.reps.toInt()
    )
}