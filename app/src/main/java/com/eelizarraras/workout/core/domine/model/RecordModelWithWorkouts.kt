package com.eelizarraras.workout.core.domine.model

import java.sql.Timestamp

data class RecordModelWithWorkouts(
    val id: Long = 0L,
    val date: Timestamp,
    val duration: Long,
    val routine: RoutineDetailModel
)
