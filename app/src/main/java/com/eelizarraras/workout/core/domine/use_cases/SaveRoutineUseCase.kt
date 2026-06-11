package com.eelizarraras.workout.core.domine.use_cases

import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.flows.routine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.model.mappers.toDomine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveRoutineUseCase(
    private val repository: DataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(routine: CreateRoutineState): LongArray = withContext(ioDispatcher) {

        val workouts = mutableListOf<WorkoutModel>()
        val workoutsSets = mutableListOf<List<WorkoutSetModel>>()

        workouts.addAll(routine.workouts.map { workout ->
            // Save all the sets ralated to the workout
            val sets = workout.sets.map { it.toDomine() }
            workoutsSets.add(sets)

            // Then return the workout
            workout.toDomine()
        })

        repository.saveRoutine(
            name = routine.name,
            workout = workouts,
            workoutSet = workoutsSets
        )
    }
}