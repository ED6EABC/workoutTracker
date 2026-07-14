package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.LoggedExerciseEntity

@Dao
interface LoggedExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg loggedExercise: LoggedExerciseEntity): LongArray

    @Query("SELECT * FROM LoggedExercise WHERE workoutSessionId = :sessionId")
    fun getExercisesForSession(sessionId: Long): List<LoggedExerciseEntity>
}