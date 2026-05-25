package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel

fun ActivityEntity.toDomine(): ActivityModel {
    return ActivityModel(
        id = this.uid,
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
        unit = this.unit,
        reps = this.reps
    )
}