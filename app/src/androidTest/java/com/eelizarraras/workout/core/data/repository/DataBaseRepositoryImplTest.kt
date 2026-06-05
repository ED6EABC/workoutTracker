package com.eelizarraras.workout.core.data.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.RoutineDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import fakes.getAWorkout
import fakes.getAWorkoutWithoutOptionalParameters
import fakes.getAWorkoutSet
import fakes.getAnActivity
import fakes.getWorkoutSetsWithDifferentUnits
import org.junit.Assert.assertThrows

@RunWith(AndroidJUnit4::class)
class DataBaseRepositoryImplTest {

    private lateinit var database: WorkoutDatabase
    private lateinit var activityDao: ActivityDao
    private lateinit var workoutDao: WorkoutDao
    private lateinit var workoutSetDao: WorkoutSetDao
    private lateinit var routineDao: RoutineDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Set up database
        database = Room.inMemoryDatabaseBuilder(context,WorkoutDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        // Set up Dao's
        activityDao = database.activityDao()
        workoutDao = database.workoutDao()
        workoutSetDao = database.workoutSetDao()
        routineDao = database.routineDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun retrieveAllWorkoutsWhenTheWorkoutsTableIsNotEmpty() = runTest {
        // Given
        val workout = getAWorkoutWithoutOptionalParameters()
        // When
        workoutDao.insert(workout)
        // Then
        val result = workoutDao.getAllWorkout().firstOrNull()
        assertEquals(workout, result)
    }

    @Test
    fun retrieveAllWorkoutsWhenTheWorkoutsTableHasEntityWithOptionalParametersNotSet() = runTest {
        // Given
        val workout = getAWorkoutWithoutOptionalParameters()
        // When
        workoutDao.insert(workout)
        // Then
        val result = workoutDao.getAllWorkout().firstOrNull()
        assertEquals(workout, result)
    }

    @Test
    fun retrieveUidFromWorkoutsWhenInsertAValidWorkout() = runTest {
        // Given
        val workout = getAWorkoutWithoutOptionalParameters()
        // When
        val result = workoutDao.insert(workout).first()
        // Then
        assertEquals(2L, result)
    }

    @Test
    fun retrieveNothingFromWorkoutsWhenTheWorkoutsTableIsEmpty() = runTest {
        // Then
        val result = workoutDao.getAllWorkout()
        assert(result.isEmpty())
    }

    @Test
    fun notRetrieveWorkoutWhenItsRemove() {
        //Given
        val workout = getAWorkout()
        workoutDao.insert(workout)

        //When
        workoutDao.delete(workout)

        //Then
        val result = workoutDao.getAllWorkout().firstOrNull()
        assert(result == null)
    }

    @Test
    fun notRetrieveWorkoutWithOptionalParametersWhenItsRemove() {
        //Given
        val workout = getAWorkoutWithoutOptionalParameters()

        workoutDao.insert(workout)

        //When
        workoutDao.delete(workout)

        //Then
        val result = workoutDao.getAllWorkout().firstOrNull()
        assert(result == null)
    }

    @Test
    fun insertTwoWorkoutsEntitiesWhenUidIsTheSame() = runTest {
        // Given
        val workout1 = getAWorkout()
        val workout2 = WorkoutEntity(
            uid = 8,
            name = "Pres banca inclinado",
            description = "Ejercicio 2....",
            note = "No olvides 2..."
        )
        // When
        workoutDao.insert(workout1)
        val uid = workoutDao.insert(workout2).first()

        // Then
        val result = workoutDao.getWorkout(uid)
        assertEquals(uid, result?.uid)
        assertEquals("Pres banca inclinado", result?.name)
        assertEquals("Ejercicio 2....", result?.description)
        assertEquals("No olvides 2...", result?.note)
    }

    @Test
    fun notGetAWorkoutEntityWhenTheTableIsEmpty() = runTest {
        // Then
        val result = workoutDao.getWorkout(1)
        assertEquals(null, result)
    }

    @Test
    fun retrieveAllWorkoutSetsWhenTheWorkoutSetsTableIsNotEmpty() {
        //Given
        val workoutSet = getAWorkoutSet()
        //When
        workoutSetDao.insert(workoutSet)
        //Then
        val result = workoutSetDao.getAllWorkoutSets().firstOrNull()
        assertEquals(workoutSet, result)
    }

    @Test
    fun retrieveAllWorkoutSetsWhenTheWorkoutSetsTableHasEntityWithPlantsUnit() {
        //Given
        val workoutSet = getAWorkoutSet(WorkoutUnit.Plates)
        //When
        workoutSetDao.insert(workoutSet)
        //Then
        val result = workoutSetDao.getAllWorkoutSets().firstOrNull()
        assertEquals(workoutSet, result)
    }

    @Test
    fun retrieveUidFromWorkoutSetWhenInsertAValidWorkoutSet() {
        //Given
        val workoutSet = getAWorkoutSet()
        //When
        val result = workoutSetDao.insert(workoutSet).first()
        //Then
        assertEquals(2L, result)
    }

    @Test
    fun retrieveNothingFromWorkoutSetsWhenTheWorkoutSetsTableIsEmpty() = runTest {
        // Then
        val result = workoutSetDao.getAllWorkoutSets()
        assert(result.isEmpty())
    }

    @Test
    fun notRetrieveWorkoutSetWhenItsRemove() {
        //Given
        val workoutSet = getAWorkoutSet()

        workoutSetDao.insert(workoutSet)

        //When
        workoutSetDao.delete(workoutSet)

        //Then
        val result = workoutSetDao.getAllWorkoutSets().firstOrNull()
        assert(result == null)
    }

    @Test
    fun insertWorkoutSetWhenTheUidIsTheSame() {
        //Given
        val workoutSet = getWorkoutSetsWithDifferentUnits().toTypedArray()
        val workoutSetToEvaluate = workoutSet.first()

        //When
        val uid = workoutSetDao.insert(*workoutSet).first()

        //Then
        val result = workoutSetDao.getWorkoutSet(uid)
        assertEquals(uid, result?.uid)

        assertEquals(workoutSetToEvaluate.weight, result?.weight)
        assertEquals(workoutSetToEvaluate.workoutUnit, result?.workoutUnit)
        assertEquals(workoutSetToEvaluate.reps, result?.reps)
    }

    @Test
    fun notRetrieveWorkoutSetWhenTheTableIsEmpty() {
        //Then
        val result = workoutSetDao.getWorkoutSet(1)
        assertEquals(null, result)
    }

    @Test
    fun getActivityWhenTheTableIsNotEmpty() {
        //Given
        val workout = getAWorkoutWithoutOptionalParameters()
        workoutDao.insert(workout)

        val workoutSet = getAWorkoutSet()
        workoutSetDao.insert(workoutSet)

        val activity = getAnActivity(2,2)
        activityDao.insert(activity)
        //When
        val result = activityDao.getActivity(6)
        //Then
        assertEquals(activity, result)
    }

    @Test
    fun retrieveUidFromActivityWhenInsertAValidActivity() {
        //Given
        val workout = getAWorkoutWithoutOptionalParameters()
        workoutDao.insert(workout)

        val workoutSet = getAWorkoutSet()
        workoutSetDao.insert(workoutSet)

        val activity = getAnActivity(2,2)

        //When
        val result = activityDao.insert(activity).first()
        //Then
        assertEquals(6L, result)
    }

    @Test
    fun retrieveNothingFromActivityWhenTheTableIsEmpty() {
        //When
        val result = activityDao.getActivity(1)
        //Then
        assertEquals(null, result)
    }

    @Test
    fun retrieveNothingFromActivityWhenTheUidIsNotFoundAnd() {
        //Given
        val workout = getAWorkout()
        workoutDao.insert(workout)

        val workoutSet = getAWorkoutSet()
        workoutSetDao.insert(workoutSet)

        val activity = getAnActivity(1,2)
        activityDao.insert(activity)
        //When
        val result = activityDao.getActivity(2)
        //Then
        assertEquals(null, result)
    }

    @Test
    fun insertTwoActivityEntityWhenItsUidIsTheSame() {
        // Given
        val workout = getAWorkout()
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = getAWorkoutSet()
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity1 = getAnActivity(1, 2)
        val activity2 = getAnActivity(1, 2)

        // When
        activityDao.insert(activity1)
        val uid = activityDao.insert(activity2).first()

        // Then
        val result = activityDao.getActivity(uid)
        assertEquals(uid, result?.uid)
        assertEquals(workoutId, result?.workoutId)
        assertEquals(workSetId, result?.setId)
    }

    @Test
    fun deleActivityWhenTheTableIsEmpty() {
        // Given
        val activity = getAnActivity(2,2)
        // When
        val result = activityDao.delete(activity)
        // Then
        assertEquals(Unit, result)
    }

    @Test
    fun deleActivityWhenTheTableIsNotEmpty() {
        // Given

        val workout = getAWorkoutWithoutOptionalParameters()
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = getAWorkoutSet()
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity = getAnActivity(workoutId,workSetId)
        activityDao.insert(activity)

        // When
        val result = activityDao.delete(activity)

        // Then
        assertEquals(Unit, result)
    }

    @Test
    fun deleteActivityWhenWorkoutSetIsDeleted() {
        // Given
        val workout = getAWorkoutWithoutOptionalParameters()
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = getAWorkoutSet()
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity = getAnActivity(workoutId,workSetId)
        val activityId = activityDao.insert(activity).first()

        // When
        workoutSetDao.delete(workoutSet)
        val result = activityDao.getActivity(activityId)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun deleteActivityWhenWorkoutIsDeleted() {
        // Given
        val workout = getAWorkoutWithoutOptionalParameters()
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = getAWorkoutSet()
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity = getAnActivity(workoutId,workSetId)
        val activityId = activityDao.insert(activity).first()

        // When
        workoutDao.delete(workout)
        val result = activityDao.getActivity(activityId)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun retrieveAnRoutineIdWhenARoutineIsCreated() {
        // Given
        val workoutId = workoutDao.insert(getAWorkoutWithoutOptionalParameters()).first()
        val workSetId = workoutSetDao.insert(getAWorkoutSet()).first()

        val activity = getAnActivity(
            workoutId = workoutId,
            workSetId = workSetId
        )
        val activityId = activityDao.insert(activity).first()

        // When
        val result = routineDao.insert(RoutineEntity(
            uid = 1L,
            name = "Pres de banco",
            activityId = activityId
        ))

        // Then
        assertEquals(1L, result)
    }

    @Test
    fun retrieveAnErrorWhenARoutineIsCreatedWithNotValidActivityId() {

        // Then
        val result = assertThrows(SQLiteConstraintException::class.java) {
            routineDao.insert(RoutineEntity(
                uid = 1L,
                name = "Pres de banco",
                activityId = 1L
            ))
        }

        assert(result.message?.contains("FOREIGN KEY constraint failed") == true)

    }
}