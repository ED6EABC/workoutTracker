package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity

@Dao
interface WorkoutDao {
    // Get all workout
    @Query ("SELECT * FROM workout")
    fun getAllWorkout(): List<WorkoutEntity>

    @Query("SELECT * FROM workout WHERE uid = :uid")
    fun getWorkout(uid: Long): WorkoutEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg workout: WorkoutEntity): LongArray

    @Delete
    fun delete(workout: WorkoutEntity)
}