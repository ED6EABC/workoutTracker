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
import com.eelizarraras.workout.core.domine.model.WorkoutUnit

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
        val result = workoutDao.insert(workout).first()
        // Then
        assertEquals(1L, result)
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
    fun insertTwoWorkoutsEntitiesWhenUidIsTheSame() = runTest {
        // Given
        val workout1 = WorkoutEntity(
            uid = 8,
            name = "Pres banca plano",
            description = "Ejercicio....",
            note = "No olvides..."
        )
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
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Kg,
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
            workoutUnit = WorkoutUnit.Plates,
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
            uid = 1L,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Plates,
            reps = 25
        )
        //When
        val result = workoutSetDao.insert(workoutSet).first()
        //Then
        assertEquals(1L, result)
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
            workoutUnit = WorkoutUnit.Lbs,
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
    fun insertWorkoutSetWhenTheUidIsTheSame() {
        //Given
        val workoutSet1 = WorkoutSetEntity(
            uid = 8,
            weight = 24.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 2
        )
        val workoutSet2 = WorkoutSetEntity(
            uid = 8,
            weight = 30.8,
            workoutUnit = WorkoutUnit.Plates,
            reps = 8
        )

        //When
        workoutSetDao.insert(workoutSet1)
        val uid = workoutSetDao.insert(workoutSet2).first()

        //Then
        val result = workoutSetDao.getWorkoutSet(uid)
        assertEquals(uid, result?.uid)
        assertEquals(30.8, result?.weight)
        assertEquals(WorkoutUnit.Plates, result?.workoutUnit)
        assertEquals(8, result?.reps)
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
            workoutUnit = WorkoutUnit.Lbs,
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
            uid = 2L,
            name = "Pres de banco",
            description = null,
            note = null
        )
        workoutDao.insert(workout)

        val workoutSet = WorkoutSetEntity(
            uid = 2L,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 3
        )
        workoutSetDao.insert(workoutSet)

        val activity = ActivityEntity(
            uid = 1L,
            workoutId = 2,
            setId = 2
        )

        //When
        val result = activityDao.insert(activity).first()
        //Then
        assertEquals(1L, result)
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
            workoutUnit = WorkoutUnit.Lbs,
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

    @Test
    fun insertTwoActivityEntityWhenItsUidIsTheSame() {
        // Given
        val workout = WorkoutEntity(
            uid = 2,
            name = "Pres de banco",
            description = null,
            note = null
        )
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 3
        )
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity1 = ActivityEntity(
            uid = 6,
            workoutId = 2,
            setId = 2
        )
        val activity2 = ActivityEntity(
            uid = 6,
            workoutId = 2,
            setId = 2
        )

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
        val activity = ActivityEntity(
            uid = 6,
            workoutId = 2,
            setId = 2
        )
        // When
        val result = activityDao.delete(activity)
        // Then
        assertEquals(Unit, result)
    }

    @Test
    fun deleActivityWhenTheTableIsNotEmpty() {
        // Given

        val workout = WorkoutEntity(
            uid = 2,
            name = "Pres de banco",
            description = null,
            note = null
        )
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 3
        )
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity = ActivityEntity(
            uid = 6,
            workoutId = workoutId,
            setId = workSetId
        )
        activityDao.insert(activity)

        // When
        val result = activityDao.delete(activity)

        // Then
        assertEquals(Unit, result)
    }

    @Test
    fun deleteActivityWhenWorkoutSetIsDeleted() {
        // Given
        val workout = WorkoutEntity(
            uid = 2,
            name = "Pres de banco",
            description = null,
            note = null
        )
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 3
        )
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity = ActivityEntity(
            uid = 6,
            workoutId = workoutId,
            setId = workSetId
        )
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
        val workout = WorkoutEntity(
            uid = 2,
            name = "Pres de banco",
            description = null,
            note = null
        )
        val workoutId = workoutDao.insert(workout).first()

        val workoutSet = WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 3
        )
        val workSetId = workoutSetDao.insert(workoutSet).first()

        val activity = ActivityEntity(
            uid = 6,
            workoutId = workoutId,
            setId = workSetId
        )
        val activityId = activityDao.insert(activity).first()

        // When
        workoutDao.delete(workout)
        val result = activityDao.getActivity(activityId)

        // Then
        assertEquals(null, result)
    }
}