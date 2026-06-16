package com.eelizarraras.workout.di

import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.routine.createRoutine.presentation.viewModel.RoutineManagerViewModel
import com.eelizarraras.workout.flows.routine.seeRoutines.presentation.viewModel.RoutineViewerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val applicationModules = module {
    viewModelOf(::NavigationViewModel)
    viewModelOf(::RoutineManagerViewModel)
    viewModel {
        RoutineViewerViewModel(
            get(),
            get(named("IODispatcher"))
        )
    }
}