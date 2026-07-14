package com.eelizarraras.workout.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eelizarraras.workout.core.data.model.dao.RoutineExerciseDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSessionDao
import com.eelizarraras.workout.core.data.model.dao.RoutineDao
import com.eelizarraras.workout.core.data.model.dao.ExerciseDao
import com.eelizarraras.workout.core.data.model.dao.RoutineSetDao
import com.eelizarraras.workout.core.data.model.dao.LoggedExerciseDao
import com.eelizarraras.workout.core.data.model.dao.LoggedSetDao
import com.eelizarraras.workout.core.data.model.entity.RoutineExerciseEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSessionEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.ExerciseEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineSetEntity
import com.eelizarraras.workout.core.data.model.entity.LoggedExerciseEntity
import com.eelizarraras.workout.core.data.model.entity.LoggedSetEntity
import com.eelizarraras.workout.core.data.model.entity.view.RoutineOverviewView

@Database(entities = [
    ExerciseEntity::class,
    RoutineSetEntity::class,
    RoutineExerciseEntity::class,
    RoutineEntity::class,
    WorkoutSessionEntity::class,
    LoggedExerciseEntity::class,
    LoggedSetEntity::class],
    views = [RoutineOverviewView::class],
    version = 2,
    exportSchema = true)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineSetDao(): RoutineSetDao
    abstract fun routineExerciseDao(): RoutineExerciseDao
    abstract fun routineDao(): RoutineDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun loggedExerciseDao(): LoggedExerciseDao
    abstract fun loggedSetDao(): LoggedSetDao
}