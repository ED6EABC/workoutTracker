package com.eelizarraras.workout.core.data.model.entity.view

import androidx.room.Embedded
import androidx.room.Relation
import com.eelizarraras.workout.core.data.model.entity.WorkoutSessionEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity

data class RecordWithRoutineEntity(
    @Embedded val session: WorkoutSessionEntity,
    @Relation(
        entity = RoutineEntity::class,
        parentColumn = "routineId",
        entityColumn = "uid"
    )
    val routine: RoutineWithWorkoutsEntity
)
