package com.eelizarraras.workout.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity

@Database(entities = [
    WorkoutEntity::class,
    WorkoutSetEntity::class,
    ActivityEntity::class], version = 1)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutSetDao(): WorkoutSetDao
    abstract fun activityDao(): ActivityDao
}