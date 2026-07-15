package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model

interface RoutineEffect {
    data class ShowLoading(val isLoading: Boolean): RoutineEffect
}