package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.RoutineDetailModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetRoutineUseCase (
    private val repository: DataBaseRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(routineId: Long): RoutineDetailModel = withContext(dispatcher){
        repository.getRoutine(routineId)
    }

}