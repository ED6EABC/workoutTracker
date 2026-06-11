package com.eelizarraras.workout.core.domine.repository

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel

interface DataBaseRepository {

    suspend fun getAllWorkouts(): List<WorkoutModel>
    suspend fun setWorkout(vararg workout: WorkoutModel): LongArray //Tested within getAllWorkouts
    suspend fun remove(workout: WorkoutEntity)

    suspend fun getAllWorkoutSets(): List<WorkoutSetModel>
    suspend fun setWorkoutSet(vararg workoutSet: WorkoutSetModel): LongArray //Tested within getAllWorkoutSets
    suspend fun remove(workoutSet: WorkoutSetEntity)

    suspend fun getActivity(uid: Long): ActivityModel?
    suspend fun setActivity(vararg activity: ActivityEntity): LongArray
    suspend fun remove(activity: ActivityEntity)

    suspend fun saveRoutine(
        name: String,
        workout: List<WorkoutModel>,
        workoutSet: List<List<WorkoutSetModel>>
    ): LongArray

}