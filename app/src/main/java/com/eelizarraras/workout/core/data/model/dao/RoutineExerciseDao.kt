package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.RoutineExerciseEntity

@Dao
interface RoutineExerciseDao {
    @Query("SELECT * FROM RoutineExercise WHERE uid = :uid")
    fun getRoutineExercise(vararg uid: Long): Array<RoutineExerciseEntity>

    @Query("SELECT COUNT(DISTINCT exerciseId) FROM RoutineExercise WHERE routineId = :uid")
    fun countExercises(uid: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg routineExercise: RoutineExerciseEntity): LongArray

    @Delete
    fun delete(routineExercise: RoutineExerciseEntity)
}