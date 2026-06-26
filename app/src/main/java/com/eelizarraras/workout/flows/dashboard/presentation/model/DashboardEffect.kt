package com.eelizarraras.workout.flows.dashboard.presentation.model

interface DashboardEffect {
    data class ShowLoading(val isLoading: Boolean) : DashboardEffect
}