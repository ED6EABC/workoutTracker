package com.eelizarraras.workout.core.data.model.entity.view

import androidx.room.Embedded
import androidx.room.Relation
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineExerciseEntity
import com.eelizarraras.workout.core.data.model.entity.ExerciseEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineSetEntity

data class RoutineWithWorkoutsEntity(
    @Embedded val routine: RoutineEntity,
    @Relation(
        entity = RoutineExerciseEntity::class,
        parentColumn = "uid",
        entityColumn = "routineId"
    )
    val exercises: List<ExerciseWithSets>
)

data class ExerciseWithSets(
    @Embedded val routineExercise: RoutineExerciseEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "uid"
    )
    val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "routineExerciseId"
    )
    val sets: List<RoutineSetEntity>
)
