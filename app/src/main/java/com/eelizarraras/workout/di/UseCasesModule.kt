package com.eelizarraras.workout.di

import com.eelizarraras.workout.core.domine.use_cases.GetRoutineUseCase
import com.eelizarraras.workout.core.domine.use_cases.GetRoutinesOverviewUseCase
import com.eelizarraras.workout.core.domine.use_cases.SaveRoutineUseCase
import com.eelizarraras.workout.core.domine.use_cases.UpdateRoutineUseCase
import com.eelizarraras.workout.flows.dashboard.domine.use_cases.GetResentRoutinesUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.SaveRecordUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.TimerUseCase
import com.eelizarraras.workout.flows.routine.seeRoutines.domine.use_cases.DeleteRoutineUseCase
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
        UpdateRoutineUseCase(
            get(),
            get(named("IODispatcher"))
        )
    }
    single {
        GetRoutinesOverviewUseCase(repository = get())
    }
    single {
        GetRoutineUseCase(
            repository = get(),
            dispatcher = get(named("IODispatcher"))
        )
    }
    single {
        TimerUseCase(
            dispatcher = get(named("IODispatcher"))
        )
    }
    single {
        GetResentRoutinesUseCase(
            repository = get(),
            dispatcher = get(named("IODispatcher"))
        )
    }
    single {
        SaveRecordUseCase(
            repository = get(),
            dispatcher = get(named("IODispatcher"))
        )
    }
    single {
        DeleteRoutineUseCase(
            repository = get(),
            dispatcher = get(named("IODispatcher"))
        )
    }
}