package com.eelizarraras.workout.core.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.eelizarraras.workout.core.domine.model.Unit

@RunWith(AndroidJUnit4::class)
class DataBaseRepositoryImplTest {

    private lateinit var database: WorkoutDatabase
    private lateinit var activityDao: ActivityDao
    private lateinit var workoutDao: WorkoutDao
    private lateinit var workoutSetDao: WorkoutSetDao

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
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun retrieveAllWorkoutsWhenTheWorkoutsTableIsNotEmpty() = runTest {
        // Given
        val workout = WorkoutEntity(
            uid = 1,
            name = "Pres banca plano",
            description = "Ejercicio....",
            note = "No olvides..."
        )
        // When
        workoutDao.insert(workout)
        // Then
        val result = workoutDao.getAllWorkout().firstOrNull()
        assertEquals(workout, result)
    }

    @Test
    fun retrieveAllWorkoutsWhenTheWorkoutsTableHasEntityWithOptionalParametersNotSet() = runTest {
        // Given
        val workout = WorkoutEntity(
            uid = 1,
            name = "Pres banca plano",
            description = null,
            note = null
        )
        // When
        workoutDao.insert(workout)
        // Then
        val result = workoutDao.getAllWorkout().firstOrNull()
        assertEquals(workout, result)
    }

    @Test
    fun retrieveUidFromWorkoutsWhenInsertAValidWorkout() = runTest {
        // Given
        val workout = WorkoutEntity(
            uid = 1,
            name = "Pres banca plano",
            description = null,
            note = null
        )
        // When
        val result = workoutDao.insert(workout)
        // Then
        assertEquals(1, result)
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
        val workout = WorkoutEntity(
            uid = 1,
            name = "Pres banca plano",
            description = "Happy training",
            note = "Something"
        )

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
        val workout = WorkoutEntity(
            uid = 1,
            name = "Pres banca plano",
            description = null,
            note = null
        )

        workoutDao.insert(workout)

        //When
        workoutDao.delete(workout)

        //Then
        val result = workoutDao.getAllWorkout().firstOrNull()
        assert(result == null)
    }

    @Test
    fun retrieveAllWorkoutSetsWhenTheWorkoutSetsTableIsNotEmpty() {
        //Given
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 10.0,
            unit = Unit.Kg,
            reps = 25
        )
        //When
        workoutSetDao.insert(workoutSet)
        //Then
        val result = workoutSetDao.getAllWorkoutSets().firstOrNull()
        assertEquals(workoutSet, result)
    }

    @Test
    fun retrieveAllWorkoutSetsWhenTheWorkoutSetsTableHasEntityWithPlantsUnit() {
        //Given
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 10.0,
            unit = Unit.Plates,
            reps = 25
        )
        //When
        workoutSetDao.insert(workoutSet)
        //Then
        val result = workoutSetDao.getAllWorkoutSets().firstOrNull()
        assertEquals(workoutSet, result)
    }

    @Test
    fun retrieveUidFromWorkoutSetWhenInsertAValidWorkoutSet() {
        //Given
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 10.0,
            unit = Unit.Plates,
            reps = 25
        )
        //When
        val result = workoutSetDao.insert(workoutSet)
        //Then
        assertEquals(1, result)
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
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 24.0,
            unit = Unit.Lbs,
            reps = 2
        )

        workoutSetDao.insert(workoutSet)

        //When
        workoutSetDao.delete(workoutSet)

        //Then
        val result = workoutSetDao.getAllWorkoutSets().firstOrNull()
        assert(result == null)
    }

    @Test
    fun getActivityWhenTheTableIsNotEmpty() {
        //Given
        val workout = WorkoutEntity(
            uid = 2,
            name = "Pres de banco",
            description = null,
            note = null
        )
        workoutDao.insert(workout)

        val workoutSet = WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            unit = Unit.Lbs,
            reps = 3
        )
        workoutSetDao.insert(workoutSet)

        val activity = ActivityEntity(
            uid = 1,
            workoutId = 2,
            setId = 2
        )
        activityDao.insert(activity)
        //When
        val result = activityDao.getActivity(1)
        //Then
        assertEquals(activity, result)
    }

    @Test
    fun retrieveUidFromActivityWhenInsertAValidActivity() {
        //Given
        val workout = WorkoutEntity(
            uid = 2,
            name = "Pres de banco",
            description = null,
            note = null
        )
        workoutDao.insert(workout)

        val workoutSet = WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            unit = Unit.Lbs,
            reps = 3
        )
        workoutSetDao.insert(workoutSet)

        val activity = ActivityEntity(
            uid = 1,
            workoutId = 2,
            setId = 2
        )

        //When
        val result = activityDao.insert(activity)
        //Then
        assertEquals(1, result)
    }

    @Test
    fun retrieveNothingFromActivityWhenTheTableIsEmpty() {
        //When
        val result = activityDao.getActivity(1)
        //Then
        assertEquals(null, result)
    }

    @Test
    fun retrieveNothingFromActivityWhenTheUidIsNotFoundTableIsEmpty() {
        //Given
        val workout = WorkoutEntity(
            uid = 2,
            name = "Pres de banco",
            description = null,
            note = null
        )
        workoutDao.insert(workout)

        val workoutSet = WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            unit = Unit.Lbs,
            reps = 3
        )
        workoutSetDao.insert(workoutSet)

        val activity = ActivityEntity(
            uid = 1,
            workoutId = 2,
            setId = 2
        )
        activityDao.insert(activity)
        //When
        val result = activityDao.getActivity(2)
        //Then
        assertEquals(null, result)
    }

}