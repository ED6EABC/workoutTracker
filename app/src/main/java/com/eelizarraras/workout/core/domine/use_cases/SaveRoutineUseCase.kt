package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.ExerciseModel
import com.eelizarraras.workout.core.domine.model.RoutineSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.flows.routine.createRoutine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.createRoutine.model.mappers.toDomine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveRoutineUseCase(
    private val repository: DataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(routine: CreateRoutineState): LongArray = withContext(ioDispatcher) {

        val exercises = mutableListOf<ExerciseModel>()
        val routineSets = mutableListOf<List<RoutineSetModel>>()

        routine.workouts.forEach { workout ->
            // Save all the sets related to the exercise
            val sets = workout.sets.map { it.toDomine() }
            routineSets.add(sets)

            // Then add the exercise
            exercises.add(workout.toDomine())
        }

        repository.saveRoutine(
            name = routine.name,
            exercises = exercises,
            routineSets = routineSets
        )
    }
}