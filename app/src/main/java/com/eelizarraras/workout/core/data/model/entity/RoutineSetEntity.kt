package com.eelizarraras.workout.core.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eelizarraras.workout.core.domine.model.WorkoutUnit

@Entity(
    tableName = "RoutineSet",
    foreignKeys = [
        ForeignKey(
            entity = RoutineExerciseEntity::class,
            parentColumns = ["uid"],
            childColumns = ["routineExerciseId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["routineExerciseId"])
    ]
)
data class RoutineSetEntity (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "routineExerciseId") val routineExerciseId: Long,
    @ColumnInfo(name = "setOrder") val setOrder: Int,
    @ColumnInfo(name = "reps") val reps: Int,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "unit") val workoutUnit: WorkoutUnit,
    @ColumnInfo(name = "isActive") val isActive: Boolean = true
)