package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository

class GetAllWorkoutSetsUseCase(
    val repository: DataBaseRepository
) {
    operator fun invoke() = repository.getAllWorkoutSets()
}