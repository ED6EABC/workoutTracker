package com.eelizarraras.workout.core.data.model.mappers

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class ToDomineTest {

    @Test
    fun `map activityEntity to domine successful`() {
        //Given
        val activity = ActivityEntity(
            uid = 1,
            workoutId = 2,
            setId = 3
        )
        //When
        val map = activity.toDomine()
        //Then
        assertAll(
            {assertEquals(1, map.id)},
            {assertEquals(2, map.workoutId)},
            {assertEquals(3, map.setId)}
        )
    }

    @Test
    fun `map WorkoutEntity to domine successful when optional fields are null`() {
        //Given
        val workout = WorkoutEntity(
            uid = 1,
            name = "SomeName",
            description = null,
            note = null
        )

        //When
        val map = workout.toDomine()
        //Then
        assertAll(
            {assertEquals(1, map.id)},
            {assertEquals("SomeName", map.name)},
            {assertEquals(null, map.description)},
            {assertEquals(null, map.note)},
        )
    }

    @Test
    fun `map WorkoutEntity to domine successful when all fields are present`() {
        //Given
        val workout = WorkoutEntity(
            uid = 1,
            name = "SomeName",
            description = "SomeDescription",
            note = "SomeNote"
        )

        //When
        val map = workout.toDomine()
        //Then
        assertAll(
            {assertEquals("SomeName", map.name)},
            {assertEquals("SomeDescription", map.description)},
            {assertEquals("SomeNote", map.note)},
        )
    }

    @Test
    fun `map WorkoutSetEntity to domine successful`() {
        //Given
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 2
        )
        //When
        val map = workoutSet.toDomine()
        //Then
        assertAll(
            {assertEquals(1, map.id)},
            {assertEquals(10.0, map.weight)},
            {assertEquals(WorkoutUnit.Lbs, map.workoutUnit)},
            {assertEquals(2, map.reps)},
        )
    }

    @Test
    fun `map WorkoutSetEntity with Kg unit successful`() {
        //Given
        val workoutSet = WorkoutSetEntity(
            uid = 1,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Kg,
            reps = 2
        )
        //When
        val map = workoutSet.toDomine()
        //Then
        assertAll(
            {assertEquals(10.0, map.weight)},
            {assertEquals(WorkoutUnit.Kg, map.workoutUnit)},
            {assertEquals(2, map.reps)},
        )
    }

}