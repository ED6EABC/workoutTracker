package com.eelizarraras.workout.core.domine.repository

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.query.RoutineOverViewEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.data.model.entity.query.RoutineWithWorkoutsEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.RecordModel
import com.eelizarraras.workout.core.domine.model.RecordModelWithWorkouts
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {

    suspend fun getAllWorkouts(): List<WorkoutModel>
    suspend fun setWorkout(vararg workout: WorkoutModel): LongArray
    suspend fun remove(workout: WorkoutEntity)

    suspend fun getAllWorkoutSets(): List<WorkoutSetModel>
    suspend fun setWorkoutSet(vararg workoutSet: WorkoutSetModel): LongArray
    suspend fun remove(workoutSet: WorkoutSetEntity)

    suspend fun getActivity(uid: Long): Array<ActivityModel>
    suspend fun setActivity(vararg activity: ActivityEntity): LongArray
    suspend fun remove(activity: ActivityEntity)

    suspend fun saveRoutine(
        name: String,
        workout: List<WorkoutModel>,
        workoutSet: List<List<WorkoutSetModel>>
    ): LongArray

    suspend fun getRoutinesOverview(): Flow<List<RoutineOverViewEntity>>

    suspend fun getRoutine(routineId: Long): Flow<RoutineWithWorkoutsEntity>

    suspend fun getMostResetRecords(limit: Int): Flow<List<RecordModelWithWorkouts>>
    suspend fun saveRecord(record: RecordModel): Long
}