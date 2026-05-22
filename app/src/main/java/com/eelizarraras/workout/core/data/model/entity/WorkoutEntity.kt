package com.eelizarraras.workout.core.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eelizarraras.workout.core.domine.model.WorkoutModel

@Entity(tableName = "Workout")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "note") val note: String? = null
)

fun WorkoutEntity.toDomine(): WorkoutModel {
    return WorkoutModel(
        name = this.name,
        description = this.description,
        note = this.note
    )
}