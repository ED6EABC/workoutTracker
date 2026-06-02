package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity

@Dao
interface RoutineDao {
    @Query("SELECT * FROM Routine WHERE uid = :uid")
    fun getRoutines(uid: Long): Array<RoutineEntity>

    @Query("SELECT * FROM Routine")
    fun getRoutine(): RoutineEntity

    @Delete
    fun delete(routine: RoutineEntity)

    @Insert
    fun insert(routine: RoutineEntity): Long
}