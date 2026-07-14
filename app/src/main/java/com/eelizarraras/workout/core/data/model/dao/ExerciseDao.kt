package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Query ("SELECT * FROM Exercise")
    fun getAllExercises(): List<ExerciseEntity>

    @Query("SELECT * FROM Exercise WHERE uid = :uid")
    fun getExercise(uid: Long): ExerciseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg exercise: ExerciseEntity): LongArray

    @Delete
    fun delete(exercise: ExerciseEntity)
}