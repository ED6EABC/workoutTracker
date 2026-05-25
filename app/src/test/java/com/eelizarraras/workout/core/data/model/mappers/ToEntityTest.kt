package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import com.eelizarraras.workout.core.domine.model.Unit

class ToEntityTest {

    @Test
    fun `map ActivityModel to ActivityEntity successful`() {
        // Given
        val activityModel = ActivityModel(
            id = 1,
            workoutId = 2,
            setId = 3
        )
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
        val workoutModel = WorkoutModel(
            id = 1,
            name = "Pres de banca",
            description = "Hazlo con ganas",
            note = "Una nota"
        )
        // When
        val result = workoutModel.toEntity()
        // Then
        assertAll(
            {assertEquals(1, result.uid)},
            {assertEquals("Pres de banca", result.name)},
            {assertEquals("Hazlo con ganas", result.description)},
            {assertEquals("Una nota", result.note)},
        )
    }

    @Test
    fun `map WorkoutModel to WorkoutEntity successful when WorkoutModel has optional parameters not set`() {
        // Given
        val workoutModel = WorkoutModel(
            id = 1,
            name = "Pres de banca",
            description = null,
            note = null
        )
        // When
        val result = workoutModel.toEntity()
        // Then
        assertAll(
            {assertEquals(1, result.uid)},
            {assertEquals("Pres de banca", result.name)},
            {assertEquals(null, result.description)},
            {assertEquals(null, result.note)},
        )
    }

    @Test
    fun `map WorkoutSetModel to WorkoutSetEntity successful`() {
        // Given
        val workoutSetModel = WorkoutSetModel(
            id = 1,
            weight = 2.0,
            unit = Unit.Plates,
            reps = 2
        )
        // When
        val result = workoutSetModel.toEntity()
        // Then
        assertAll(
            {assertEquals(1, result.uid)},
            {assertEquals(2.0, result.weight)},
            {assertEquals(Unit.Plates, result.unit)},
            {assertEquals(2, result.reps)},
        )
    }

    @Test
    fun `map WorkoutSetModel to WorkoutSetEntity successful when the unit is Kg`() {
        // Given
        val workoutSetModel = WorkoutSetModel(
            id = 1,
            weight = 2.0,
            unit = Unit.Kg,
            reps = 2
        )
        // When
        val result = workoutSetModel.toEntity()
        // Then
        assertAll(
            {assertEquals(1, result.uid)},
            {assertEquals(2.0, result.weight)},
            {assertEquals(Unit.Kg, result.unit)},
            {assertEquals(2, result.reps)},
        )
    }

}