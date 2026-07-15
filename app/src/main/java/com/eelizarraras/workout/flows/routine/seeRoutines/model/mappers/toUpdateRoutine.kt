package com.eelizarraras.workout.flows.routine.seeRoutines.model.mappers

import com.eelizarraras.workout.core.domine.model.RoutineDetailModel
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.Workout

fun RoutineDetailModel.toCreateRoutineState(): CreateRoutineState {
    return CreateRoutineState(
        routineId = this.id,
        name = this.name,
        workouts = this.workouts.map { workout ->
            Workout(
                uid = workout.id.toString(),
                name = workout.name,
                sets = workout.sets.map { set ->
                    WorkoutSet(
                        uid = set.id.toString(),
                        weight = set.weight.toString(),
                        workoutUnit = set.workoutUnit,
                        reps = set.reps.toString()
                    )
                }
            )
        }
    )
}