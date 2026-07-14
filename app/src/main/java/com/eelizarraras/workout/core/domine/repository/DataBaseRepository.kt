package com.eelizarraras.workout.core.domine.repository

import com.eelizarraras.workout.core.data.model.entity.ExerciseEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineExerciseEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineSetEntity
import com.eelizarraras.workout.core.domine.model.*
import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {

    // Exercise
    suspend fun getAllExercises(): List<ExerciseModel>
    suspend fun setExercise(vararg exercise: ExerciseModel): LongArray
    suspend fun remove(exercise: ExerciseEntity)

    // RoutineSet
    suspend fun getAllRoutineSets(): List<RoutineSetModel>
    suspend fun setRoutineSet(vararg routineSet: RoutineSetModel): LongArray
    suspend fun remove(routineSet: RoutineSetEntity)

    // RoutineExercise
    suspend fun getRoutineExercise(uid: Long): Array<RoutineExerciseModel>
    suspend fun setRoutineExercise(vararg routineExercise: RoutineExerciseEntity): LongArray
    suspend fun remove(routineExercise: RoutineExerciseEntity)

    suspend fun saveRoutine(
        name: String,
        exercises: List<ExerciseModel>,
        routineSets: List<List<RoutineSetModel>>
    ): LongArray

    suspend fun getRoutinesOverview(): Flow<List<RoutineOverView>>

    suspend fun getRoutine(routineId: Long): Flow<RoutineDetailModel>

    suspend fun getMostResentRecords(limit: Int): Flow<List<RecordOverViewModel>>
    suspend fun saveRecord(record: RecordModel): Long

    suspend fun deleteRoutine(routineId: Long): Int
}