package com.eelizarraras.workout.core.domine.model

data class RecordModel(
    val id: Long = 0L,
    val date: Long,
    val duration: Long,
    val routineId: Long
)
