package com.eelizarraras.workout.core.domine.use_cases

import android.util.Log
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.flows.routine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.model.mappers.toDomine

class SaveRoutineUseCase(
    private val repository: DataBaseRepository
) {
    suspend operator fun invoke(routine: CreateRoutineState) {
        //TODO right now the scope is only save a routines (workout and WorkoutSet)

        var workoutSet: Array<WorkoutSetModel> = arrayOf()
        val workouts = routine.workouts.map { workout ->
            workoutSet = workout.sets.map { workoutSet -> workoutSet.toDomine() }.toTypedArray()
            workout.toDomine()
        }.toTypedArray()

        //Save workout
        val ids = repository.setWorkout(*workouts)

        //Save workout set
        val workoutSetIds = repository.setWorkoutSet(*workoutSet)

        Log.d("TEST","id: $ids, workoutSetIds: $workoutSetIds")

    }
}