package com.eelizarraras.workout.flows.routine.seeRoutines.model

import com.eelizarraras.workout.core.domine.model.Screen

sealed class RoutineViewerEvent {
    data class AddRoutine(val screen: Screen): RoutineViewerEvent()
    object PlayRoutine: RoutineViewerEvent()
    object GetRoutines: RoutineViewerEvent()
}