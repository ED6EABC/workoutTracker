package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.mappers

import com.eelizarraras.workout.core.domine.model.ExerciseModel
import com.eelizarraras.workout.core.domine.model.RoutineSetModel
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.Workout

fun Workout.toDomine(): ExerciseModel {
    return ExerciseModel(
        id = this.uid.toLongOrNull() ?: 0L,
        name = this.name,
        note = ""
    )
}

fun WorkoutSet.toDomine(): RoutineSetModel {
    return RoutineSetModel(
        id = this.uid.toLongOrNull() ?: 0L,
        routineExerciseId = 0L,
        setOrder = 0, // Should be set during saving
        weight = this.weight.toDoubleOrNull() ?: 0.0,
        workoutUnit = this.workoutUnit,
        reps = this.reps.toIntOrNull() ?: 0
    )
}