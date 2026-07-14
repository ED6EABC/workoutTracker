package com.eelizarraras.workout.core.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eelizarraras.workout.core.domine.model.WorkoutUnit

@Entity(
    tableName = "LoggedSet",
    foreignKeys = [
        ForeignKey(
            entity = LoggedExerciseEntity::class,
            parentColumns = ["uid"],
            childColumns = ["loggedExerciseId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["loggedExerciseId"])
    ]
)
data class LoggedSetEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "loggedExerciseId") val loggedExerciseId: Long,
    @ColumnInfo(name = "setOrder") val setOrder: Int,
    @ColumnInfo(name = "reps") val reps: Int,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "unit") val workoutUnit: WorkoutUnit,
    @ColumnInfo(name = "isComplete") val isComplete: Boolean
)