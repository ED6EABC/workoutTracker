package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository

class SaveSetUseCase(
    val repository: DataBaseRepository
) {
    operator fun invoke(vararg workoutSet: WorkoutSetModel) {
        return repository.setWorkoutSet(*workoutSet)
    }
}