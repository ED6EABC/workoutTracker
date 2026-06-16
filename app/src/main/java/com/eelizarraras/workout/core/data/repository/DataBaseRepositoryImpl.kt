package com.eelizarraras.workout.core.data.repository

import androidx.room.withTransaction
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.RoutineDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineOverViewEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.data.model.mappers.toDomine
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import com.eelizarraras.workout.core.data.model.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataBaseRepositoryImpl(
    val workoutDatabase: WorkoutDatabase,
    val workoutDao: WorkoutDao,
    val workoutSetDao: WorkoutSetDao,
    val activityDao: ActivityDao,
    val routineDao: RoutineDao
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
    override suspend fun getActivity(uid: Long): Array<ActivityModel> {
        return activityDao.getActivity(uid).map { it.toDomine() }.toTypedArray()
    }

    override suspend fun setActivity(vararg activity: ActivityEntity): LongArray {
        return activityDao.insert(*activity)
    }

    override suspend fun remove(activity: ActivityEntity) {
        activityDao.delete(activity)
    }

    override suspend fun saveRoutine(
        name: String,
        workout: List<WorkoutModel>,
        workoutSet: List<List<WorkoutSetModel>>
    ): LongArray {
        return workoutDatabase.withTransaction {
            val activitiesIds = mutableListOf<Long>()
            val routineId = routineDao.insert(RoutineEntity(uid = 0L, name = name))
            val workoutIds = workoutDao.insert(*workout.map { it.toEntity() }.toTypedArray())

            workoutSet.forEachIndexed { index, workoutSetList ->
                val toEntity = workoutSetList.map { it.toEntity() }
                val workoutSetIds = workoutSetDao.insert(*toEntity.toTypedArray())

                val activities = workoutSetIds.map { setId ->
                    ActivityEntity(
                        uid = 0L,
                        routineId = routineId,
                        workoutId = workoutIds[index],
                        setId = setId
                    )
                }
                activitiesIds.addAll(activityDao.insert(*activities.toTypedArray()).toList())
            }
            activitiesIds.toLongArray()
        }
    }

    override suspend fun getRoutinesOverview(): Flow<List<RoutineOverViewEntity>> {
        return workoutDatabase.withTransaction {
            routineDao.getRoutines().map { arrayOfRoutinesOverViewEntity ->
                arrayOfRoutinesOverViewEntity.map { routine ->


                    RoutineOverViewEntity(
                        id = routine.uid,
                        name = routine.name,
                        workouts = activityDao.countWorkouts(routine.uid)
                    )
                }
            }
        }
    }
}