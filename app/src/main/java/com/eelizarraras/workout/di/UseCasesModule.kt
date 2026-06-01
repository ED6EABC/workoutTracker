package com.eelizarraras.workout.di

import com.eelizarraras.workout.core.domine.use_cases.SaveRoutineUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
val useCasesModule = module {
    singleOf(::SaveRoutineUseCase)
}