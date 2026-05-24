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

class DataBaseRepositoryImpl(
    val workoutDao: WorkoutDao,
    val workoutSetDao: WorkoutSetDao,
    val activityDao: ActivityDao
): DataBaseRepository {

    // Workout
    override fun getAllWorkouts(): List<WorkoutModel> {
        return workoutDao.getAllWorkout().map { it.toDomine() }
    }

    override fun setWorkout(workout: WorkoutEntity) {
        workoutDao.insert(workout)
    }

    override fun remove(workout: WorkoutEntity) {
        workoutDao.delete(workout)
    }

    // Set
    override fun getAllWorkoutSets(): List<WorkoutSetModel> {
        return workoutSetDao.getAllWorkoutSets().map { it.toDomine() }
    }

    override fun setWorkout(workoutSet: WorkoutSetEntity) {
        workoutSetDao.insert(workoutSet)
    }

    override fun remove(workoutSet: WorkoutSetEntity) {
        workoutSetDao.delete(workoutSet)
    }

    // Activity
    override fun getActivity(uid: Int): ActivityModel? {
        return activityDao.getActivity(uid).takeIf { it != null }?.toDomine()
    }

    override fun setActivity(activity: ActivityEntity) {
        activityDao.insert(activity)
    }

    override fun remove(activity: ActivityEntity) {
        activityDao.delete(activity)
    }
}