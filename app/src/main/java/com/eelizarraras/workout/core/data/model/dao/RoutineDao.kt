package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.view.RoutineOverviewView
import com.eelizarraras.workout.core.data.model.entity.view.RoutineWithWorkoutsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine WHERE uid = :uid")
    fun getRoutine(uid: Long): RoutineEntity

    /**
     * Optimized for list screens. Uses a database view to fetch pre-calculated 
     * summaries (workout counts, last record date) in a single efficient query.
     */
    @Query("SELECT * FROM RoutineOverviewView")
    fun getRoutinesOverview(): Flow<List<RoutineOverviewView>>

    /**
     * Optimized for detail/play screens. Fetches the full hierarchy of 
     * Routine -> Activities -> Workouts -> Sets. 
     * Note: Expensive to call on large lists due to multiple underlying queries.
     */
    @Transaction
    @Query("SELECT * FROM routine WHERE uid = :routineId")
    fun getRoutineWithActivities(routineId: Long): Flow<RoutineWithWorkoutsEntity>

    @Query("UPDATE routine SET isActive = :isActive WHERE uid = :routineId")
    fun delete(isActive: Boolean = false, routineId: Long): Int

    @Insert
    fun insert(routine: RoutineEntity): Long
}