package com.eelizarraras.workout.core.data.model.entity.view

import androidx.room.Embedded
import androidx.room.Relation
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity

data class RoutineWithWorkoutsEntity(
    @Embedded val routine: RoutineEntity,
    @Relation(
        entity = ActivityEntity::class,
        parentColumn = "uid",
        entityColumn = "routineId"
    )
    val activities: List<WorkoutWithSets>
)

data class WorkoutWithSets(
    @Embedded val activity: ActivityEntity,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "uid"
    )
    val workout: WorkoutEntity,
    @Relation(
        parentColumn = "setId",
        entityColumn = "uid"
    )
    val sets: WorkoutSetEntity

)
