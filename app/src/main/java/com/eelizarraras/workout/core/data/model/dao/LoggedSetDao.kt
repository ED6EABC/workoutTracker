package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.LoggedSetEntity

@Dao
interface LoggedSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg loggedSet: LoggedSetEntity): LongArray

    @Query("SELECT * FROM LoggedSet WHERE loggedExerciseId = :loggedExerciseId")
    fun getSetsForLoggedExercise(loggedExerciseId: Long): List<LoggedSetEntity>
}