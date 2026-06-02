package com.eelizarraras.workout.core.data.repository

import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.data.model.mappers.toDomine
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.core.data.model.mappers.toEntity

class DataBaseRepositoryImpl(
    val workoutDao: WorkoutDao,
    val workoutSetDao: WorkoutSetDao,
    val activityDao: ActivityDao
): DataBaseRepository {

    // Workout
    override suspend fun getAllWorkouts(): List<WorkoutModel> {
        return workoutDao.getAllWorkout().map { it.toDomine() }
    }

    override suspend fun setWorkout(vararg workout: WorkoutModel): LongArray {
        val workoutEntity = workout.map { it.toEntity() }.toTypedArray()
        return workoutDao.insert(*workoutEntity)
    }

    override suspend fun remove(workout: WorkoutEntity) {
        workoutDao.delete(workout)
    }

    // Set
    override suspend fun getAllWorkoutSets(): List<WorkoutSetModel> {
        return workoutSetDao.getAllWorkoutSets().map { it.toDomine() }
    }

    override suspend fun setWorkoutSet(vararg workoutSet: WorkoutSetModel): LongArray {
        val workoutSetEntity = workoutSet.map { it.toEntity() }.toTypedArray()
        return workoutSetDao.insert(*workoutSetEntity)
    }

    override suspend fun remove(workoutSet: WorkoutSetEntity) {
        workoutSetDao.delete(workoutSet)
    }

    // Activity
    override suspend fun getActivity(uid: Long): ActivityModel? {
        return activityDao.getActivity(uid).takeIf { it != null }?.toDomine()
    }

    override suspend fun setActivity(vararg activity: ActivityEntity): LongArray {
        return activityDao.insert(*activity)
    }

    override suspend fun remove(activity: ActivityEntity) {
        activityDao.delete(activity)
    }


}