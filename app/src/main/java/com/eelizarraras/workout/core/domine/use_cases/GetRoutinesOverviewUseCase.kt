package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetRoutinesOverviewUseCase(
    val repository: DataBaseRepository
) {
    suspend operator fun invoke(): Flow<List<RoutineModel>> {
        return repository.getRoutinesOverview().map { arrayOfRoutineOverViewEntity ->
            arrayOfRoutineOverViewEntity.map {
                RoutineModel(
                    id = it.id,
                    name = it.name,
                    workouts = it.workouts,
                    durationInMinutes = ""
                )
            }
        }
    }
}