package com.eelizarraras.workout.di

import com.eelizarraras.workout.core.viewModel.NavigationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val applicationModules = module {
    viewModelOf(::NavigationViewModel)
}