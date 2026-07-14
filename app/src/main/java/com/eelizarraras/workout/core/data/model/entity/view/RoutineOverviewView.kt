package com.eelizarraras.workout.core.data.model.entity.view

import androidx.room.DatabaseView

/**
 * Optimized database view for routine overviews. 
 * Encapsulates the complex logic of counting exercises and fetching the latest session.
 */
@DatabaseView("""
    SELECT 
        r.uid, 
        r.name, 
        (SELECT COUNT(DISTINCT exerciseId) FROM RoutineExercise WHERE routineId = r.uid) AS exerciseCount,
        ws.duration,
        ws.date
    FROM Routine r
    LEFT JOIN WorkoutSession ws ON ws.uid = (
        SELECT uid FROM WorkoutSession 
        WHERE routineId = r.uid 
        ORDER BY date DESC 
        LIMIT 1
    )
""")
data class RoutineOverviewView(
    val uid: Long,
    val name: String,
    val exerciseCount: Int,
    val duration: Long?,
    val date: Long?
)
