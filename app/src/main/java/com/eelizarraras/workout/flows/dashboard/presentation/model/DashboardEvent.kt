package com.eelizarraras.workout.flows.dashboard.presentation.model

sealed class DashboardEvent {
    object LoadResentRoutines : DashboardEvent()
    data class OnPlayRoutine(val routineId: Long) : DashboardEvent()
    object SeeMore: DashboardEvent()
}