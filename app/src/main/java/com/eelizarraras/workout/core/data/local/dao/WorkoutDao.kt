package com.eelizarraras.workout.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eelizarraras.workout.core.data.local.entity.WorkoutEntity

@Dao
interface WorkoutDao {
    // Get all workout
    @Query ("SELECT * FROM workout")
    fun getAllWorkout(): List<WorkoutEntity>

    @Insert
    fun insert(workout: WorkoutEntity)

    @Delete
    fun delete(workout: WorkoutEntity)
}