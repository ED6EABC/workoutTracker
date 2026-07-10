package com.eelizarraras.workout.core.data.model.entity.view

import androidx.room.DatabaseView

/**
 * Optimized database view for routine overviews. 
 * Encapsulates the complex logic of counting workouts and fetching the latest record.
 */
@DatabaseView("""
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
data class RoutineOverviewView(
    val uid: Long,
    val name: String,
    val workoutCount: Int,
    val duration: Long?,
    val date: Long?
)
