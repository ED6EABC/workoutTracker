package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.data.model.entity.*
import com.eelizarraras.workout.core.domine.model.*

fun RoutineExerciseModel.toEntity(): RoutineExerciseEntity {
    return RoutineExerciseEntity(
        uid = this.id,
        routineId = this.routineId,
        exerciseId = this.exerciseId,
        sortOrder = this.sortOrder,
        restTimeInSeconds = this.restTimeInSeconds
    )
}

fun ExerciseModel.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        uid = this.id,
        name = this.name,
        note = this.note,
        isActive = this.isActive
    )
}

fun RoutineSetModel.toEntity(): RoutineSetEntity {
    return RoutineSetEntity(
        uid = this.id,
        routineExerciseId = this.routineExerciseId,
        setOrder = this.setOrder,
        weight = this.weight,
        workoutUnit = this.workoutUnit,
        reps = this.reps,
        isActive = this.isActive
    )
}

fun RecordModel.toEntity(): WorkoutSessionEntity {
    return WorkoutSessionEntity(
        uid = this.id,
        date = this.date,
        duration = this.duration,
        routineId = this.routineId,
        name = this.name
    )
}

fun LoggedExerciseModel.toEntity(sessionId: Long): LoggedExerciseEntity {
    return LoggedExerciseEntity(
        uid = this.id,
        workoutSessionId = sessionId,
        exerciseId = this.exerciseId,
        sortOrder = this.sortOrder
    )
}

fun LoggedSetModel.toEntity(loggedExerciseId: Long): LoggedSetEntity {
    return LoggedSetEntity(
        uid = this.id,
        loggedExerciseId = loggedExerciseId,
        setOrder = this.setOrder,
        reps = this.reps,
        weight = this.weight,
        workoutUnit = this.unit,
        isComplete = this.isComplete
    )
}
