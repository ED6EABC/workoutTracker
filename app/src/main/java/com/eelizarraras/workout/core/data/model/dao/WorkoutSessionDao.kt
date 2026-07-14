package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eelizarraras.workout.core.data.model.entity.WorkoutSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {

    @Transaction
    @Query("SELECT * FROM WorkoutSession GROUP BY routineId ORDER BY date DESC LIMIT :limit")
    fun getSessions(limit: Int): Flow<Array<WorkoutSessionEntity>> // Note: RecordWithRoutineEntity will need update

    @Insert
    fun insert(session: WorkoutSessionEntity): Long
}