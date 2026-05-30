package com.eelizarraras.workout.di

import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.routine.presentation.viewModel.RoutineManagerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val applicationModules = module {
    viewModelOf(::NavigationViewModel)
    viewModelOf(::RoutineManagerViewModel)
}