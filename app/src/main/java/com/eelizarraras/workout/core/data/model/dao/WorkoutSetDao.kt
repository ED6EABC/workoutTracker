package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity

@Dao
interface WorkoutSetDao {
    @Query("SELECT * FROM workoutset")
    fun getAllWorkoutSets(): List<WorkoutSetEntity>

    @Query("SELECT * FROM workoutset WHERE uid = :uid")
    fun getWorkoutSet(uid: Long): WorkoutSetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workoutSet: WorkoutSetEntity): Long

    @Delete
    fun delete(workoutSet: WorkoutSetEntity)
}