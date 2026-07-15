package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.ExerciseModel
import com.eelizarraras.workout.core.domine.model.RoutineSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.mappers.toDomine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateRoutineUseCase(
    private val repository: DataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(routine: CreateRoutineState) = withContext(ioDispatcher) {
        val exercises = mutableListOf<ExerciseModel>()
        val routineSets = mutableListOf<List<RoutineSetModel>>()

        routine.workouts.forEach { workout ->
            val sets = workout.sets.map { it.toDomine() }
            routineSets.add(sets)
            exercises.add(workout.toDomine())
        }

        repository.updateRoutine(
            routineId = routine.routineId,
            name = routine.name,
            exercises = exercises,
            routineSets = routineSets
        )
    }
}
