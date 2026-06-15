package com.eelizarraras.workout.core.data.repository

import androidx.room.withTransaction
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.RoutineDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.mappers.toEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import fakes.getActivityEntity
import fakes.getRoutinesEntity
import fakes.getWorkoutEntity
import fakes.getWorkoutModel
import fakes.getWorkoutModelList
import fakes.getWorkoutModelWithOptionalParameters
import fakes.getWorkoutSet
import fakes.getWorkoutSetEntity
import fakes.getWorkoutSetModel
import fakes.getWorkoutSetModelList
import fakes.getWorkouts
import fakes.getWorkoutsWithOptionalParameters
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.arrayOf

class DataBaseRepositoryImplTest {

    var workoutDatabase: WorkoutDatabase = mockk()
    var workoutDao: WorkoutDao = mockk()
    var workoutSetDao: WorkoutSetDao = mockk()
    var activityDao: ActivityDao = mockk()
    var routineDao: RoutineDao = mockk()

    lateinit var dataBaseRepository: DataBaseRepository

    @BeforeEach
    fun setUp() {
        // This allows to mock the static method Room.databaseBuilder
        mockkStatic("androidx.room.RoomDatabaseKt")
        val transactionLambda = slot<suspend () -> Any?>()

        coEvery {
            workoutDatabase.withTransaction(capture(transactionLambda))
        } coAnswers  {
            transactionLambda.captured.invoke()
        }

        dataBaseRepository = DataBaseRepositoryImpl(
            workoutDatabase,
            workoutDao,
            workoutSetDao,
            activityDao,
            routineDao,
        )
    }

    @AfterEach
    fun teardown() {
        unmockkStatic("androidx.room.RoomDatabaseKt")
        unmockkAll()
    }

    @Test
    suspend fun returnWorkoutModelsWhenDAOReturnsWorkoutEntities() {
        // Given
        val workouts = getWorkouts()

        coEvery { workoutDao.getAllWorkout() } returns workouts

        // When
        val result = dataBaseRepository.getAllWorkouts().first()

        // Then
        assertAll(
            { Assertions.assertEquals(1, result.id) },
            { Assertions.assertEquals(workouts.first().name, result.name) },
            { Assertions.assertEquals(workouts.first().description, result.description) },
            { Assertions.assertEquals(workouts.first().note, result.note) },
        )

        coVerify(exactly = 1) { workoutDao.getAllWorkout() }
    }

    @Test
    suspend fun returnWorkoutModelsWhenDAOReturnsWorkoutEntitiesWithOptionalParameters() {
        // Given
        val workouts = getWorkoutsWithOptionalParameters()

        coEvery { workoutDao.getAllWorkout() } returns workouts

        // When
        val result = dataBaseRepository.getAllWorkouts()

        // Then
        assertAll(
            { Assertions.assertEquals(1, result.first().id) },
            { Assertions.assertEquals(workouts.first().name, result.first().name) },
            { Assertions.assertEquals(null, result.first().description) },
            { Assertions.assertEquals(null, result.last().note) },
        )

        coVerify(exactly = 1) { workoutDao.getAllWorkout() }
    }

    @Test
    suspend fun returnAnEmptyWorkoutModelListWhenDAOReturnsAnEmptyWorkoutEntityList() {
        // Given
        coEvery { workoutDao.getAllWorkout() } returns listOf()

        // When
        val result = dataBaseRepository.getAllWorkouts()

        // Then
        assertAll(
            { Assertions.assertEquals(listOf<WorkoutModel>(), result) },
        )

        coVerify(exactly = 1) { workoutDao.getAllWorkout() }
    }

    @Test
    suspend fun returnWorkoutSetModelsWhenDAOReturnsWorkoutSetEntity() {
        //Given
        val workoutSet = getWorkoutSet()
        coEvery { workoutSetDao.getAllWorkoutSets() } returns listOf(workoutSet)

        //When
        val result = dataBaseRepository.getAllWorkoutSets().first()

        //Then
        assertAll(
            { Assertions.assertEquals(workoutSet.uid, result.id)},
            { Assertions.assertEquals(workoutSet.weight, result.weight)},
            { Assertions.assertEquals(workoutSet.workoutUnit, result.workoutUnit)},
            { Assertions.assertEquals(workoutSet.reps, result.reps)},
        )

        coVerify(exactly = 1) { workoutSetDao.getAllWorkoutSets() }
    }

    @Test
    suspend fun returnAnEmptyWorkoutSetModelListWhenDAOReturnsAnEmptyWorkoutSetEntityList() {
        //Given
        coEvery { workoutSetDao.getAllWorkoutSets() } returns listOf()

        //When
        val result = dataBaseRepository.getAllWorkoutSets()

        //Then
        Assertions.assertEquals(listOf<WorkoutSetModel>(), result)

        coVerify(exactly = 1) { workoutSetDao.getAllWorkoutSets() }
    }

    @Test
    suspend fun returnActivityModelWhenDAOReturnsActivityEntity() {
        //Given
        val uid: Long = 1
        val activityEntity = getActivityEntity()
        coEvery { activityDao.getActivity(uid) } returns arrayOf(activityEntity)

        //When
        val result = dataBaseRepository.getActivity(uid).first()

        //Then
        assertAll(
            { Assertions.assertEquals(activityEntity.uid, result.id)},
            { Assertions.assertEquals(activityEntity.workoutId, result.workoutId)},
            { Assertions.assertEquals(activityEntity.setId, result.setId)},
        )

        coVerify(exactly = 1) { activityDao.getActivity(uid) }
    }

    @Test
    suspend fun returnAnEmptyArrayWhenActivityEntityIsEmpty() {
        //Given
        coEvery { activityDao.getActivity(1) } returns arrayOf()

        //When
        val result = dataBaseRepository.getActivity(1)

        //Then
        assert(result.isEmpty())

        coVerify(exactly = 1) { activityDao.getActivity(1) }
    }

    @Test
    suspend fun verifySetWorkoutCallsWorkoutDaoInsert() {
        // Given
        val workout = getWorkoutModelWithOptionalParameters()
        coEvery { workoutDao.insert(any()) } returns longArrayOf(1L)

        // When
        dataBaseRepository.setWorkout(workout)

        // Then
        coVerify(exactly = 1) { workoutDao.insert(workout.toEntity()) }
    }

    @Test
    suspend fun verifyRemoveWorkoutCallsWorkoutDaoDelete() {
        // Given
        val workout = getWorkoutEntity()
        coEvery { workoutDao.delete(any()) } returns Unit

        // When
        dataBaseRepository.remove(workout)

        // Then
        coVerify(exactly = 1) { workoutDao.delete(workout) }
    }

    @Test
    suspend fun verifySetWorkoutSetCallsWorkoutSetDaoInsert() {
        // Given
        val workoutSet = getWorkoutSetModel()
        coEvery { workoutSetDao.insert(any()) } returns longArrayOf(1L)

        // When
        dataBaseRepository.setWorkoutSet(workoutSet)

        // Then
        coVerify(exactly = 1) { workoutSetDao.insert(workoutSet.toEntity()) }
    }

    @Test
    suspend fun verifyRemoveWorkoutSetCallsWorkoutSetDaoDelete() {
        // Given
        val workoutSet = getWorkoutSetEntity()
        coEvery { workoutSetDao.delete(any()) } returns Unit

        // When
        dataBaseRepository.remove(workoutSet)

        // Then
        coVerify(exactly = 1) { workoutSetDao.delete(workoutSet) }
    }

    @Test
    suspend fun verifySetActivityCallsActivityDaoInsert() {
        // Given
        val activity = getActivityEntity()
        coEvery { activityDao.insert(any()) } returns longArrayOf(1L)

        // When
        dataBaseRepository.setActivity(activity)

        // Then
        coVerify(exactly = 1) { activityDao.insert(activity) }
    }

    @Test
    suspend fun verifyRemoveActivityCallsActivityDaoDelete() {
        // Given
        val activity = getActivityEntity()
        coEvery { activityDao.delete(any()) } returns Unit

        // When
        dataBaseRepository.remove(activity)

        // Then
        coVerify(exactly = 1) { activityDao.delete(activity) }
    }

    @Test
    suspend fun verifySetRoutineCallsAllRelatedDaoFunctionsWhenARoutineHasMoreThanOneSet() {
        // Given
        val name = "Test"

        val workouts = getWorkoutModelList()
        val workoutSet = getWorkoutSetModelList()

        coEvery { routineDao.insert(any()) } returns 1L
        coEvery { workoutDao.insert(*anyVararg()) } returns longArrayOf(1L)
        coEvery { workoutSetDao.insert(*anyVararg()) } returns longArrayOf(1L)
        coEvery { activityDao.insert(any()) } returns longArrayOf(1L)

        // When
        dataBaseRepository.saveRoutine(
            name,
            workouts,
            workoutSet
        )

        // Then
        coVerify(exactly = 1) {
            routineDao.insert(match { it.name == name })
        }

        coVerify(exactly = 1) {
            workoutDao.insert(*anyVararg())
        }

        // Test if the workoutSets are inserted as one argument
        coVerify(exactly = 2) {
            workoutSetDao.insert(*anyVararg())
        }

        coVerify(exactly = 2) {
            activityDao.insert(any())
        }

        coVerify(exactly = 1) {
            routineDao.insert(any())
        }
    }

    @Test
    suspend fun verifySaveRoutineCallsAllRelatedDaoFunctionsWithJustOneWorkoutAndOneSet() {
        // Given
        val name = "Test"

        val workout = listOf(getWorkoutModel())
        val listWorkoutSet = listOf(getWorkoutSetModel())

        coEvery { routineDao.insert(any()) } returns 1L
        coEvery { workoutDao.insert(any()) } returns longArrayOf(1L)
        coEvery { workoutSetDao.insert(any()) } returns longArrayOf(1L)
        coEvery { activityDao.insert(any()) } returns longArrayOf(1L)

        // When
        dataBaseRepository.saveRoutine(
            name,
            workout,
            listOf(listWorkoutSet)
        )

        // Then
        coVerify(exactly = 1) {
            routineDao.insert(match { it.name == name })
        }

        coVerify(exactly = 1) {
            workoutDao.insert(any())
        }

        // Test if the workoutSets are inserted as one argument
        coVerify(exactly = 1) {
            workoutSetDao.insert(any())
        }

        coVerify(exactly = 1) {
            activityDao.insert(any())
        }

        coVerify(exactly = 1) {
            routineDao.insert(any())
        }
    }

    @Test
    fun verifyGetRoutinesOverviewCallsAllRelatedDaoFunctions() = runTest {
        // Given
        coEvery { routineDao.getRoutines() } returns getRoutinesEntity()
        coEvery { activityDao.countWorkouts(any()) } returns 1 andThen 2

        // When
        val result = dataBaseRepository.getRoutinesOverview()

        // Then
        assertAll(
            { assert(result.size == 2) },
            { Assertions.assertEquals(result.first().name, "Lunes de pecho") },
            { Assertions.assertEquals(result.first().workouts, 1) },
            { Assertions.assertEquals(result.last().name, "Martes de pierna") },
            { Assertions.assertEquals(result.last().workouts, 2) }
        )

        coVerify(exactly = 1) { routineDao.getRoutines() }
        coVerify(exactly = 2) { activityDao.countWorkouts(any()) }

    }
}