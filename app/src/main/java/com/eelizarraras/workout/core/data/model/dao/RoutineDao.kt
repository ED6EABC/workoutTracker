package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.view.RoutineOverviewEntity
import com.eelizarraras.workout.core.data.model.entity.view.RoutineWithWorkoutsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine WHERE uid = :uid")
    fun getRoutine(uid: Long): RoutineEntity

    @Query("""
        SELECT 
            r.uid, 
            r.name, 
            (SELECT COUNT(DISTINCT workoutId) FROM Activity WHERE routineId = r.uid) AS workoutCount,
            rec.duration,
            rec.date
        FROM Routine r
        LEFT JOIN Record rec ON rec.uid = (
            SELECT uid FROM Record 
            WHERE routineId = r.uid 
            ORDER BY date DESC 
            LIMIT 1
        )
    """)
    fun getRoutinesOverview(): Flow<List<RoutineOverviewEntity>>

    @Transaction
    @Query("SELECT * FROM routine WHERE uid = :routineId")
    fun getRoutineRelatedToAnId(routineId: Long): Flow<RoutineWithWorkoutsEntity>

    @Delete
    fun delete(routine: RoutineEntity)

    @Insert
    fun insert(routine: RoutineEntity): Long
}