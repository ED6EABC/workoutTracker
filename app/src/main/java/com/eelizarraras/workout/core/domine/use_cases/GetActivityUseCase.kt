package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository

class GetActivityUseCase(
    val repository: DataBaseRepository
) {
    suspend operator fun invoke(uid: Long): ActivityModel? {
        return repository.getActivity(uid)
    }
}