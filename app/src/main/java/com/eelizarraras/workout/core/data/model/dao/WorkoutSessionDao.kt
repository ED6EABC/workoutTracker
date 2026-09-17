package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eelizarraras.workout.core.data.model.entity.WorkoutSessionEntity
import com.eelizarraras.workout.core.data.model.entity.view.RecordWithRoutineEntity
import com.eelizarraras.workout.core.data.model.entity.view.LoggedSetWithDetailsTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {

    @Transaction
    @Query("SELECT * FROM WorkoutSession GROUP BY routineId ORDER BY date DESC LIMIT :limit")
    fun getSessions(limit: Int): Flow<List<RecordWithRoutineEntity>>

    @Insert
    fun insert(session: WorkoutSessionEntity): Long

    @Query("SELECT * FROM WorkoutSession ORDER BY date DESC")
    fun getAllSessions(): Flow<List<WorkoutSessionEntity>>

    @Query("""
        SELECT e.name AS exerciseName, s.weight AS weight, s.unit AS unit, ws.date AS date
        FROM LoggedSet s
        JOIN LoggedExercise le ON s.loggedExerciseId = le.uid
        JOIN Exercise e ON le.exerciseId = e.uid
        JOIN WorkoutSession ws ON le.workoutSessionId = ws.uid
    """)
    fun getAllLoggedSetsWithDetails(): Flow<List<LoggedSetWithDetailsTuple>>
}