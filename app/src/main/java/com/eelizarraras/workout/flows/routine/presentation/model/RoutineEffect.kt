package com.eelizarraras.workout.flows.routine.presentation.model

interface RoutineEffect {
    data class ShowLoading(val isLoading: Boolean): RoutineEffect
}