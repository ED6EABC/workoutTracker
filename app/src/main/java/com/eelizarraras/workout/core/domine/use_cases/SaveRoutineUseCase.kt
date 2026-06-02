package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.flows.routine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.model.mappers.toDomine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveRoutineUseCase(
    private val repository: DataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(routine: CreateRoutineState) = withContext(ioDispatcher) {
        //TODO right now the scope is only save a routines (workout and WorkoutSet)

        routine.workouts.forEach { workout ->
            val workoutAsDomine = workout.toDomine()
            val workoutSet = workout.sets.map { workoutSet -> workoutSet.toDomine() }.toTypedArray()

            repository.saveRoutine(routine.name, workoutAsDomine, *workoutSet)
        }
    }
}