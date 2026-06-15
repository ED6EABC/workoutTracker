package com.eelizarraras.workout.di

import com.eelizarraras.workout.core.domine.use_cases.GetRoutinesOverviewUseCase
import com.eelizarraras.workout.core.domine.use_cases.SaveRoutineUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module
val useCasesModule = module {
    single {
        SaveRoutineUseCase(
        get(),
        get(named("IODispatcher"))
        )
    }
    single {
        GetRoutinesOverviewUseCase(
            repository = get(),
            get(named("IODispatcher"))
        )
    }
}