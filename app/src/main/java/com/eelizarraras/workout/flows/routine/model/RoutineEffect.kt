package com.eelizarraras.workout.flows.routine.model

interface RoutineEffect {
    data class ShowLoading(val isLoading: Boolean): RoutineEffect
}