package com.eelizarraras.workout.core.data.model.mappers

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import fakes.getActivityModel
import fakes.getWorkoutModel
import fakes.getWorkoutModelWithOptionalParameters
import fakes.getWorkoutSetModel

class ToEntityTest {

    @Test
    fun `map ActivityModel to ActivityEntity successful`() {
        // Given
        val activityModel = getActivityModel()
        // When
        val result = activityModel.toEntity()
        // Then
        assertAll(
            {assertEquals(1, result.uid)},
            {assertEquals(2, result.workoutId)},
            {assertEquals(3, result.setId)},
        )
    }

    @Test
    fun `map WorkoutModel to WorkoutEntity successful`() {
        // Given
        val workoutModel = getWorkoutModel()
        // When
        val result = workoutModel.toEntity()
        // Then
        assertAll(
            {assertEquals(workoutModel.id, result.uid)},
            {assertEquals(workoutModel.name, result.name)},
            {assertEquals(workoutModel.description, result.description)},
            {assertEquals(workoutModel.note, result.note)},
        )
    }

    @Test
    fun `map WorkoutModel to WorkoutEntity successful when WorkoutModel has optional parameters not set`() {
        // Given
        val workoutModel = getWorkoutModelWithOptionalParameters()
        // When
        val result = workoutModel.toEntity()
        // Then
        assertAll(
            {assertEquals(workoutModel.id, result.uid)},
            {assertEquals(workoutModel.name, result.name)},
            {assertEquals(null, result.description)},
            {assertEquals(null, result.note)},
        )
    }

    @Test
    fun `map WorkoutSetModel to WorkoutSetEntity successful`() {
        // Given
        val workoutSetModel = getWorkoutSetModel()
        // When
        val result = workoutSetModel.toEntity()
        // Then
        assertAll(
            {assertEquals(workoutSetModel.id, result.uid)},
            {assertEquals(workoutSetModel.weight, result.weight)},
            {assertEquals(workoutSetModel.workoutUnit, result.workoutUnit)},
            {assertEquals(workoutSetModel.reps, result.reps)},
        )
    }

    @Test
    fun `map WorkoutSetModel to WorkoutSetEntity successful when the unit is Kg`() {
        // Given
        val workoutSetModel = getWorkoutSetModel(WorkoutUnit.Kg)
        // When
        val result = workoutSetModel.toEntity()
        // Then
        assertAll(
            {assertEquals(workoutSetModel.id, result.uid)},
            {assertEquals(workoutSetModel.weight, result.weight)},
            {assertEquals(workoutSetModel.workoutUnit, result.workoutUnit)},
            {assertEquals(workoutSetModel.reps, result.reps)},
        )
    }

    @Test
    fun `map RoutineModel to RoutineEntity successful`() {
        // Given
        val activityModel = getActivityModel()
        // When
        val result = activityModel.toEntity()
        // Then
        assertAll(
            {assertEquals(activityModel.id, result.uid)},
            {assertEquals(activityModel.workoutId, result.workoutId)},
            {assertEquals(activityModel.setId, result.setId)}
        )
    }

}