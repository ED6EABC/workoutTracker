package com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case

import com.eelizarraras.workout.core.domine.model.RecordModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveRecordUseCase(
    private val repository: DataBaseRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        duration: Long,
        routineId: Long,
    ): Long = withContext(dispatcher) {
        repository.saveRecord(RecordModel(
            date = System.currentTimeMillis(),
            duration = duration,
            routineId = routineId
        ))
    }
}