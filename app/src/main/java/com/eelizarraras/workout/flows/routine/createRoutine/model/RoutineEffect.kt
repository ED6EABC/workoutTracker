package com.eelizarraras.workout.flows.routine.createRoutine.model

interface RoutineEffect {
    data class ShowLoading(val isLoading: Boolean): RoutineEffect
}