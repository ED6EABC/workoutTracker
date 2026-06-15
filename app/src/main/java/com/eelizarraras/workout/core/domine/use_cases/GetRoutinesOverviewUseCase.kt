package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRoutinesOverviewUseCase(
    val repository: DataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<RoutineModel> = withContext(ioDispatcher) {
        repository.getRoutinesOverview().map {
            RoutineModel(
                id = it.id,
                name = it.name,
                workouts = it.workouts,
                durationInMinutes = ""
            )
        }
    }
}