package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.RoutineSetEntity
import com.eelizarraras.workout.core.domine.model.WorkoutUnit

@Dao
interface RoutineSetDao {
    @Query("SELECT * FROM RoutineSet")
    fun getAllRoutineSets(): List<RoutineSetEntity>

    @Query("SELECT * FROM RoutineSet WHERE uid = :uid")
    fun getRoutineSet(uid: Long): RoutineSetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg routineSet: RoutineSetEntity): LongArray

    @Query("UPDATE RoutineSet SET weight = :weight, reps = :reps, unit = :unit WHERE uid = :setId")
    suspend fun updateSet(setId: Long, weight: Double, reps: Int, unit: WorkoutUnit)

    @Delete
    fun delete(routineSet: RoutineSetEntity)
}