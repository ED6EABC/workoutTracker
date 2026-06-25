package com.eelizarraras.workout.core.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Record")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "routineId") val activityId: Long,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "duration") val duration: Long
)