package com.eelizarraras.workout.core.domine.model

import java.sql.Timestamp

data class RecordModel(
    val id: Long,
    val date: Timestamp,
    val duration: Long,
    val routine: RoutineDetailModel
)
