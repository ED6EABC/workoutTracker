package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher

class SaveSetUseCase(
    val repository: DataBaseRepository,
    val io: CoroutineDispatcher
) {
    suspend operator fun invoke(vararg workoutSet: WorkoutSetModel) {
        repository.setWorkoutSet(*workoutSet)
    }
}