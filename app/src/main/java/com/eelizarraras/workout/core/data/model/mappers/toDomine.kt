package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.data.model.entity.*
import com.eelizarraras.workout.core.data.model.entity.view.RecordWithRoutineEntity
import com.eelizarraras.workout.core.data.model.entity.view.RoutineOverviewView
import com.eelizarraras.workout.core.data.model.entity.view.RoutineWithWorkoutsEntity
import com.eelizarraras.workout.core.domine.model.*

fun RoutineExerciseEntity.toDomine(): RoutineExerciseModel {
    return RoutineExerciseModel(
        id = this.uid,
        routineId = this.routineId,
        exerciseId = this.exerciseId,
        sortOrder = this.sortOrder
    )
}

fun ExerciseEntity.toDomine(): ExerciseModel {
    return ExerciseModel(
        id = this.uid,
        name = this.name,
        note = this.note,
        isActive = this.isActive
    )
}

fun RoutineSetEntity.toDomine(): RoutineSetModel {
    return RoutineSetModel(
        id = this.uid,
        routineExerciseId = this.routineExerciseId,
        setOrder = this.setOrder,
        weight = this.weight,
        workoutUnit = this.workoutUnit,
        reps = this.reps,
        isActive = this.isActive
    )
}

fun RoutineWithWorkoutsEntity.toDomine(): RoutineDetailModel {
    val exercisesMapped = exercises.map { exerciseWithSets ->
        WorkoutWithSetsModel(
            id = exerciseWithSets.exercise.uid,
            name = exerciseWithSets.exercise.name,
            note = exerciseWithSets.exercise.note,
            sets = exerciseWithSets.sets.map { it.toDomine() }
        )
    }

    return RoutineDetailModel(
        id = routine.uid,
        name = routine.name,
        workouts = exercisesMapped // Keeping the name 'workouts' for now to avoid cascading changes
    )
}

fun RecordWithRoutineEntity.toDomine(): RecordOverViewModel {
    val exerciseCount = routine.exercises.size

    return RecordOverViewModel(
        id = session.uid,
        routineName = routine.routine.name,
        date = session.date,
        duration = session.duration,
        workouts = exerciseCount // Keeping 'workouts' for compatibility
    )
}

fun RoutineEntity.toDomine(exerciseCount: Int): RoutineOverView {
    return RoutineOverView(
        id = uid,
        name = name,
        workouts = exerciseCount
    )
}

fun RoutineOverviewView.toDomine(): RoutineOverView {
    return RoutineOverView(
        id = uid,
        name = name,
        workouts = exerciseCount,
        duration = duration,
        date = date
    )
}