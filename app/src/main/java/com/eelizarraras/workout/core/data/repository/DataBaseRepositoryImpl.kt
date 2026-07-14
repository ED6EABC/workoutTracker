package com.eelizarraras.workout.core.data.repository

import androidx.room.withTransaction
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
import com.eelizarraras.workout.core.data.model.dao.*
import com.eelizarraras.workout.core.data.model.entity.*
import com.eelizarraras.workout.core.data.model.mappers.toDomine
import com.eelizarraras.workout.core.data.model.mappers.toEntity
import com.eelizarraras.workout.core.domine.model.*
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataBaseRepositoryImpl(
    private val workoutDatabase: WorkoutDatabase,
    private val exerciseDao: ExerciseDao,
    private val routineSetDao: RoutineSetDao,
    private val routineExerciseDao: RoutineExerciseDao,
    private val routineDao: RoutineDao,
    private val workoutSessionDao: WorkoutSessionDao
) : DataBaseRepository {

    // Exercise
    override suspend fun getAllExercises(): List<ExerciseModel> {
        return exerciseDao.getAllExercises().map { it.toDomine() }
    }

    override suspend fun setExercise(vararg exercise: ExerciseModel): LongArray {
        val exerciseEntity = exercise.map { it.toEntity() }.toTypedArray()
        return exerciseDao.insert(*exerciseEntity)
    }

    override suspend fun remove(exercise: ExerciseEntity) {
        exerciseDao.delete(exercise)
    }

    // RoutineSet
    override suspend fun getAllRoutineSets(): List<RoutineSetModel> {
        return routineSetDao.getAllRoutineSets().map { it.toDomine() }
    }

    override suspend fun setRoutineSet(vararg routineSet: RoutineSetModel): LongArray {
        val routineSetEntity = routineSet.map { it.toEntity() }.toTypedArray()
        return routineSetDao.insert(*routineSetEntity)
    }

    override suspend fun remove(routineSet: RoutineSetEntity) {
        routineSetDao.delete(routineSet)
    }

    // RoutineExercise
    override suspend fun getRoutineExercise(uid: Long): Array<RoutineExerciseModel> {
        return routineExerciseDao.getRoutineExercise(uid).map { it.toDomine() }.toTypedArray()
    }

    override suspend fun setRoutineExercise(vararg routineExercise: RoutineExerciseEntity): LongArray {
        return routineExerciseDao.insert(*routineExercise)
    }

    override suspend fun remove(routineExercise: RoutineExerciseEntity) {
        routineExerciseDao.delete(routineExercise)
    }

    override suspend fun saveRoutine(
        name: String,
        exercises: List<ExerciseModel>,
        routineSets: List<List<RoutineSetModel>>
    ): LongArray {
        return workoutDatabase.withTransaction {
            val routineId = routineDao.insert(RoutineEntity(uid = 0L, name = name))
            val exerciseIds = exerciseDao.insert(*exercises.map { it.toEntity() }.toTypedArray())
            val routineExerciseIds = mutableListOf<Long>()

            exercises.forEachIndexed { index, exercise ->
                val exerciseId = exerciseIds[index]
                val routineExerciseId = routineExerciseDao.insert(
                    RoutineExerciseEntity(
                        uid = 0L,
                        routineId = routineId,
                        exerciseId = exerciseId,
                        sortOrder = index
                    )
                )[0]
                routineExerciseIds.add(routineExerciseId)

                val sets = routineSets[index].map {
                    it.toEntity().copy(routineExerciseId = routineExerciseId)
                }
                routineSetDao.insert(*sets.toTypedArray())
            }
            routineExerciseIds.toLongArray()
        }
    }

    override suspend fun getRoutinesOverview(): Flow<List<RoutineOverView>> {
        return routineDao.getRoutinesOverview().map { arrayOfRoutinesOverViewEntity ->
            arrayOfRoutinesOverViewEntity.map { it.toDomine() }
        }
    }

    override suspend fun getRoutine(routineId: Long): Flow<RoutineDetailModel> {
        return routineDao.getRoutineWithActivities(routineId).map { it.toDomine() }
    }

    override suspend fun getMostResentRecords(limit: Int): Flow<List<RecordOverViewModel>> {
        return workoutSessionDao.getSessions(limit).map { sessions ->
            // Note: Update RecordWithRoutineEntity mapping if needed
            sessions.map { 
                // Placeholder until RecordWithRoutineEntity is updated to use WorkoutSession
                RecordOverViewModel(it.uid, it.name, it.date, it.duration, 0)
            }
        }
    }

    override suspend fun saveRecord(record: RecordModel): Long {
        return workoutSessionDao.insert(record.toEntity())
    }

    override suspend fun deleteRoutine(routineId: Long): Int {
        return routineDao.delete(routineId = routineId)
    }

}