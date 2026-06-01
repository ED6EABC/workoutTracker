package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel

class SaveWorkoutUseCase(
    val repository: DataBaseRepository
) {
    operator fun invoke(vararg workout: WorkoutModel) = repository.setWorkout(*workout)
}