package com.eelizarraras.workout.flows.routine.playRoutine.presentation.model

interface PlayRoutineEffect {

    data class ShowLoading(val isLoading: Boolean) : PlayRoutineEffect
    object ShowConfirmationDialog : PlayRoutineEffect

}