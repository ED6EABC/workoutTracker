package com.eelizarraras.workout.core.domine.repository

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel

interface DataBaseRepository {

    fun getAllWorkouts(): List<WorkoutModel>
    fun setWorkout(workout: WorkoutEntity) //Tested within getAllWorkouts
    fun remove(workout: WorkoutEntity)

    fun getAllWorkoutSets(): List<WorkoutSetModel>
    fun setWorkout(workoutSet: WorkoutSetEntity) //Tested within getAllWorkoutSets
    fun remove(workoutSet: WorkoutSetEntity)

    fun getActivity(uid: Long): ActivityModel?
    fun setActivity(activity: ActivityEntity)
    fun remove(activity: ActivityEntity)

}