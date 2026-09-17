package com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.core.presentation.model.WorkoutSetToUpdate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateWorkoutSetUseCase(
    private val repository: DataBaseRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(setsToUpdate: List<WorkoutSetToUpdate>) = withContext(dispatcher) {
        setsToUpdate.forEach { setToUpdate ->
            val setId = setToUpdate.setId.toLongOrNull() ?: return@forEach
            val weight = setToUpdate.weight.toDoubleOrNull() ?: 0.0
            val reps = setToUpdate.reps.toIntOrNull() ?: 0
            val unit = setToUpdate.workoutUnit ?: return@forEach

            repository.updateSet(setId, weight, reps, unit)
        }
    }
}
