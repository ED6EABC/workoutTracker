package com.eelizarraras.workout.core.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eelizarraras.workout.core.domine.model.Unit
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel

@Entity(tableName = "WorkoutSet")
data class WorkoutSetEntity (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "unit") val unit: Unit,
    @ColumnInfo(name = "reps") val reps: Int
)

fun WorkoutSetEntity.toDomine(): WorkoutSetModel {
    return WorkoutSetModel(
        weight = this.weight,
        unit = this.unit,
        reps = this.reps
    )
}