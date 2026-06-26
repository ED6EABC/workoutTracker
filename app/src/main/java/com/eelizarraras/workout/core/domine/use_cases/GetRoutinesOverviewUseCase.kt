package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.flows.routine.seeRoutines.model.mappers.toRoutineModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRoutinesOverviewUseCase(
    val repository: DataBaseRepository
) {
    suspend operator fun invoke(): Flow<List<RoutineModel>> {
        return repository.getRoutinesOverview().map { arrayOfRoutineOverView ->
            arrayOfRoutineOverView.map { it.toRoutineModel() }
        }
    }
}