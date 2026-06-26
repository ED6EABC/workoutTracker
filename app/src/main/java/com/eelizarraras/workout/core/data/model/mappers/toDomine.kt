package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.data.model.entity.view.RecordWithRoutineEntity
import com.eelizarraras.workout.core.data.model.entity.view.RoutineOverviewEntity
import com.eelizarraras.workout.core.data.model.entity.view.RoutineWithWorkoutsEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.RecordOverViewModel
import com.eelizarraras.workout.core.domine.model.RoutineDetailModel
import com.eelizarraras.workout.core.domine.model.RoutineOverView
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

fun RecordWithRoutineEntity.toDomine(): RecordOverViewModel {
    val workouts = routine.activities.distinctBy { it.workout.uid }.size

    return RecordOverViewModel(
        id = record.uid,
        routineName = routine.routine.name,
        date = record.date,
        duration = record.duration,
        workouts = workouts
    )
}

fun RoutineEntity.toDomine(workouts: Int): RoutineOverView {
    return RoutineOverView(
        id = uid,
        name = name,
        workouts = workouts
    )
}

fun RoutineOverviewEntity.toDomine(): RoutineOverView {
    return RoutineOverView(
        id = uid,
        name = name,
        workouts = workoutCount,
        duration = duration,
        date = date
    )
}