package com.eelizarraras.workout.core.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eelizarraras.workout.core.domine.model.WorkoutUnit

@Entity(tableName = "WorkoutSet")
data class WorkoutSetEntity (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "unit") val workoutUnit: WorkoutUnit,
    @ColumnInfo(name = "reps") val reps: Int
)