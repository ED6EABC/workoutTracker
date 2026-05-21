package com.eelizarraras.workout.core.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Activity",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["uid"],
            childColumns = ["workoutId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = WorkoutSetEntity::class,
            parentColumns = ["uid"],
            childColumns = ["setId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "workoutId") val workoutId: Int,
    @ColumnInfo(name = "setId") val setId: Int
)
