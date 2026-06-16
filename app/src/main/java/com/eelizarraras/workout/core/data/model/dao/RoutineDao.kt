package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine WHERE uid = :uid")
    fun getRoutine(uid: Long): RoutineEntity

    @Query("SELECT * FROM routine")
    fun getRoutines(): Flow<Array<RoutineEntity>>

    @Delete
    fun delete(routine: RoutineEntity)

    @Insert
    fun insert(routine: RoutineEntity): Long
}