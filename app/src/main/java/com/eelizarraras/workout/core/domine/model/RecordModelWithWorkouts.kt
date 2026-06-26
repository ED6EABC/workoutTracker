package com.eelizarraras.workout.core.domine.model

data class RecordOverViewModel(
    val id: Long = 0L,
    val routineName: String,
    val date: Long,
    val duration: Long,
    val workouts: Int
)