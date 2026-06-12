package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository

class GetRoutinesUseCase(
    val repository: DataBaseRepository
) {
    suspend operator fun invoke() = repository.getRoutinesOverview()
}