package com.eelizarraras.workout.flows.routine.seeRoutines.model.mappers

import com.eelizarraras.workout.core.domine.model.RoutineDetailModel
import com.eelizarraras.workout.core.domine.model.RoutineOverView
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.model.WorkoutWithSetsModel
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineState
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.Workout
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.WorkoutSetWithCheck

fun RoutineDetailModel.toPlayRoutineState(): PlayRoutineState {
    val workouts = this.workouts.map { it.toWorkOut() }

    return PlayRoutineState(
        routineName = this.name,
        routineId = this.id,
        workouts = workouts,
        workoutsTotal = this.workouts.size
    )
}

fun WorkoutWithSetsModel.toWorkOut(): Workout {
    return Workout(
        id = this.id.toString(),
        name = this.name,
        sets = this.sets.map { it.toWorkoutSetWithCheck() },
        setsTotal = this.sets.size.toString()
    )
}

fun WorkoutSetModel.toWorkoutSetWithCheck(): WorkoutSetWithCheck {
    val workoutSet = WorkoutSet(
        uid = this.id.toString(),
        weight = this.weight.toString(),
        workoutUnit = this.workoutUnit,
        reps = this.reps.toString()
    )

    return WorkoutSetWithCheck(
        workoutSet = workoutSet,
        isChecked = false
    )
}

// TODO add the missing fields
fun RoutineOverView.toRoutineModel(): RoutineModel {
    return RoutineModel(
        id = this.id,
        name = this.name,
        workouts = this.workouts.toString(),
        durationInMinutes = "",
        weekDayName = ""
    )
}