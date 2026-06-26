package com.eelizarraras.workout.flows.dashboard.presentation.model.mappers

import com.eelizarraras.workout.core.domine.model.RecordOverViewModel
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.core.domine.utils.getWeekDayName
import com.eelizarraras.workout.core.domine.utils.toMinutes

fun RecordOverViewModel.toPresentation(): RoutineModel{
    return RoutineModel(
        id = this.id,
        name = this.routineName,
        workouts = this.workouts.toString(),
        durationInMinutes = this.duration.toMinutes().toString(),
        weekDayName = this.date.getWeekDayName()
    )
}