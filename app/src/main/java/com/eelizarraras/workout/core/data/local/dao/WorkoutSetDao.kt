package com.eelizarraras.workout.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eelizarraras.workout.core.data.local.entity.WorkoutSetEntity

@Dao
interface WorkoutSetDao {
    @Query("SELECT * FROM workoutset")
    fun getAllWorkoutSets(): List<WorkoutSetEntity>

    @Insert
    fun insert(workout: WorkoutSetEntity)

    @Delete
    fun delete(workout: WorkoutSetEntity)
}