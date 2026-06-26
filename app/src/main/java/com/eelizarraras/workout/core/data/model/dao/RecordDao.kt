package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eelizarraras.workout.core.data.model.entity.RecordEntity
import com.eelizarraras.workout.core.data.model.entity.query.RecordWithRoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Transaction
    @Query("SELECT * FROM record GROUP BY routineId ORDER BY date DESC LIMIT :limit")
    fun getRecords(limit: Int): Flow<Array<RecordWithRoutineEntity>>

    @Insert
    fun insert(record: RecordEntity): Long
}