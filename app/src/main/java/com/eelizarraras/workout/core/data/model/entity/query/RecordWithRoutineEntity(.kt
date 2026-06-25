package com.eelizarraras.workout.core.data.model.entity.query

import androidx.room.Embedded
import androidx.room.Relation
import com.eelizarraras.workout.core.data.model.entity.RecordEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity

data class RecordWithRoutineEntity(
    @Embedded val record: RecordEntity,
    @Relation(
        entity = RoutineEntity::class,
        parentColumn = "routineId",
        entityColumn = "uid"
    )
    val routine: RoutineWithWorkoutsEntity
)
