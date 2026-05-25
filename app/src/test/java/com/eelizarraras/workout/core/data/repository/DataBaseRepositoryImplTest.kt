package com.eelizarraras.workout.core.data.repository

import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.Unit
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class DataBaseRepositoryImplTest {

    var workoutDao: WorkoutDao = mockk()
    var workoutSetDao: WorkoutSetDao = mockk()
    var activityDao: ActivityDao = mockk()

    lateinit var dataBaseRepository: DataBaseRepository

    @BeforeEach
    fun setUp() {
        dataBaseRepository = DataBaseRepositoryImpl(
            workoutDao,
            workoutSetDao,
            activityDao
        )
    }

    @AfterEach
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun returnWorkoutModelsWhenDAOReturnsWorkoutEntities() {
        // Given
        val workout = WorkoutEntity(
            uid = 1,
            name = "Pres de banco plano",
            description = "Un ejercicio...",
            note = "Hacer el ejercicio con ganas"
        )
        val workout2 = WorkoutEntity(
            uid = 2,
            name = "Sentadillas",
            description = "Un ejercicio...",
            note = "Hacer el ejercicio con ganas"
        )

        coEvery { workoutDao.getAllWorkout() } returns listOf(workout, workout2)

        // When
        val result = dataBaseRepository.getAllWorkouts().first()

        // Then
        assertAll(
            { Assertions.assertEquals(1, result.id) },
            { Assertions.assertEquals(workout.name, result.name) },
            { Assertions.assertEquals(workout.description, result.description) },
            { Assertions.assertEquals(workout.note, result.note) },
        )

        coVerify(exactly = 1) { workoutDao.getAllWorkout() }
    }

    @Test
    fun returnWorkoutModelsWhenDAOReturnsWorkoutEntitiesWithOptionalParameters() {
        // Given
        val workout = WorkoutEntity(
            uid = 1,
            name = "Pres de banco plano",
            description = null,
            note = "Hacer el ejercicio con ganas"
        )
        val workout2 = WorkoutEntity(
            uid = 2,
            name = "Sentadillas",
            description = "Un ejercicio...",
            note = null
        )

        coEvery { workoutDao.getAllWorkout() } returns listOf(workout, workout2)

        // When
        val result = dataBaseRepository.getAllWorkouts()

        // Then
        assertAll(
            { Assertions.assertEquals(1, result.first().id) },
            { Assertions.assertEquals(workout.name, result.first().name) },
            { Assertions.assertEquals(null, result.first().description) },
            { Assertions.assertEquals(null, result.last().note) },
        )

        coVerify(exactly = 1) { workoutDao.getAllWorkout() }
    }

    @Test
    fun returnAnEmptyWorkoutModelListWhenDAOReturnsAnEmptyWorkoutEntityList() {
        // Given
        coEvery { workoutDao.getAllWorkout() } returns listOf()

        // When
        val result = dataBaseRepository.getAllWorkouts()

        // Then
        assertAll(
            { Assertions.assertEquals(listOf<WorkoutModel>(), result) }
        )

        coVerify(exactly = 1) { workoutDao.getAllWorkout() }
    }

    @Test
    fun returnWorkoutSetModelsWhenDAOReturnsWorkoutSetEntity() {
        //Given
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 80.0,
            unit = Unit.Kg,
            reps = 15
        )
        coEvery { workoutSetDao.getAllWorkoutSets() } returns listOf(workoutSet)

        //When
        val result = dataBaseRepository.getAllWorkoutSets().first()

        //Then
        assertAll(
            { Assertions.assertEquals(workoutSet.uid, result.id)},
            { Assertions.assertEquals(workoutSet.weight, result.weight)},
            { Assertions.assertEquals(workoutSet.unit, result.unit)},
            { Assertions.assertEquals(workoutSet.reps, result.reps)},
        )

        coVerify(exactly = 1) { workoutSetDao.getAllWorkoutSets() }
    }

    @Test
    fun returnAnEmptyWorkoutSetModelListWhenDAOReturnsAnEmptyWorkoutSetEntityList() {
        //Given
        coEvery { workoutSetDao.getAllWorkoutSets() } returns listOf()

        //When
        val result = dataBaseRepository.getAllWorkoutSets()

        //Then
        Assertions.assertEquals(listOf<WorkoutSetModel>(), result)

        coVerify(exactly = 1) { workoutSetDao.getAllWorkoutSets() }
    }

    @Test
    fun returnActivityModelWhenDAOReturnsActivityEntity() {
        //Given
        val uid: Long = 1
        val activityEntity = ActivityEntity(
            uid = uid,
            workoutId = 2,
            setId = 3
        )
        coEvery { activityDao.getActivity(uid) } returns activityEntity

        //When
        val result = dataBaseRepository.getActivity(uid)

        //Then
        assertAll(
            { Assertions.assertEquals(activityEntity.uid, result?.id)},
            { Assertions.assertEquals(activityEntity.workoutId, result?.workoutId)},
            { Assertions.assertEquals(activityEntity.setId, result?.setId)},
        )

        coVerify(exactly = 1) { activityDao.getActivity(uid) }
    }

    @Test
    fun returnNullWhenActivityEntityIsEmpty() {
        //Given
        coEvery { activityDao.getActivity(1) } returns null

        //When
        val result = dataBaseRepository.getActivity(1)

        //Then
        Assertions.assertEquals(null, result?.id)

        coVerify(exactly = 1) { activityDao.getActivity(1) }
    }

}