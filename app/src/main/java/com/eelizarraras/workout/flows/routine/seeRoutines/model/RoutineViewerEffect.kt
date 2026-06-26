package com.eelizarraras.workout.flows.routine.seeRoutines.model

import com.eelizarraras.workout.core.presentation.model.Screen

interface RoutineViewerEffect {
    data class ShowLoading(val isLoading: Boolean): RoutineViewerEffect
    data class OnNavigateTo(val screen: Screen): RoutineViewerEffect
}