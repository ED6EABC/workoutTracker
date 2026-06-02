package com.eelizarraras.workout.core.domine.use_cases

import android.util.Log
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.flows.routine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.model.mappers.toDomine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.internal.throwMissingFieldException

class SaveRoutineUseCase(
    private val repository: DataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(routine: CreateRoutineState) = withContext(ioDispatcher) {
        //TODO right now the scope is only save a routines (workout and WorkoutSet)

        routine.workouts.forEach { workout ->
            val workoutAsDomine = workout.toDomine()
            val workoutSet = workout.sets.map { workoutSet -> workoutSet.toDomine() }.toTypedArray()

            val workoutId = repository.setWorkout(workoutAsDomine).firstOrNull()

            if(workoutId != null) {
                val activities = repository.setWorkoutSet(*workoutSet).map { setId ->
                    ActivityEntity(uid = 0L, workoutId = workoutId, setId = setId)
                }.toTypedArray()

                repository.setActivity(*activities)
            } else throw Exception() //TODO

        }
    }
}