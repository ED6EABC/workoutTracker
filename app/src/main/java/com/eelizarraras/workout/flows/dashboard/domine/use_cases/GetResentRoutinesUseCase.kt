package com.eelizarraras.workout.flows.dashboard.domine.use_cases

import com.eelizarraras.workout.core.domine.model.RecordModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

const val LIMIT = 5

class GetResentRoutinesUseCase(
    private val repository: DataBaseRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<List<RecordModel>> = withContext(dispatcher){
        repository.getMostResetRecords(LIMIT)
    }
}