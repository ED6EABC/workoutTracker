package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.RecordEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.RecordModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel

fun ActivityModel.toEntity(): ActivityEntity {
    return ActivityEntity(
        uid = this.id,
        routineId = this.routineId,
        workoutId = this.workoutId,
        setId = this.setId
    )
}

fun WorkoutModel.toEntity(): WorkoutEntity {
    return WorkoutEntity(
        uid = this.id,
        name = this.name,
        note = this.note
    )
}

fun WorkoutSetModel.toEntity(): WorkoutSetEntity {
    return WorkoutSetEntity(
        uid = this.id,
        weight = this.weight,
        workoutUnit = this.workoutUnit,
        reps = this.reps
    )
}

fun RecordModel.toEntity(): RecordEntity {
    return RecordEntity(
        uid = this.id,
        date = this.date,
        duration = this.duration,
        routineId = this.routineId
    )
}