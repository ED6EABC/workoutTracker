package com.eelizarraras.workout.flows.routine.seeRoutines.domine.use_cases

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteRoutineUseCase(
    val repository: DataBaseRepository,
    val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(routineId: Long): Int = withContext(dispatcher) {
        repository.deleteRoutine(routineId)
    }
}