package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.data.model.entity.query.RoutineWithWorkoutsEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.RoutineDetailModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.model.WorkoutWithSetsModel

fun ActivityEntity.toDomine(): ActivityModel {
    return ActivityModel(
        id = this.uid,
        routineId = this.routineId,
        workoutId = this.workoutId,
        setId = this.setId
    )
}

fun WorkoutEntity.toDomine(): WorkoutModel {
    return WorkoutModel(
        id = this.uid,
        name = this.name,
        description = this.description,
        note = this.note
    )
}

fun WorkoutSetEntity.toDomine(): WorkoutSetModel {
    return WorkoutSetModel(
        id = this.uid,
        weight = this.weight,
        workoutUnit = this.workoutUnit,
        reps = this.reps
    )
}
fun RoutineWithWorkoutsEntity.toDomine(): RoutineDetailModel {
    val workoutsGrouped = activities.groupBy { it.workout.uid }
        .map { (workoutId, activities) ->
            val workoutEntity = activities.first().workout
            WorkoutWithSetsModel(
                id = workoutEntity.uid,
                name = workoutEntity.name,
                description = workoutEntity.description,
                note = workoutEntity.note,
                sets = activities.map { it.sets.toDomine() }
            )
        }

    return RoutineDetailModel(
        id = routine.uid,
        name = routine.name,
        workouts = workoutsGrouped
    )
}