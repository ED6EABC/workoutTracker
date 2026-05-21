package com.eelizarraras.workout.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eelizarraras.workout.core.data.local.dao.ActivityDao
import com.eelizarraras.workout.core.data.local.dao.WorkoutDao
import com.eelizarraras.workout.core.data.local.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.local.entity.ActivityEntity
import com.eelizarraras.workout.core.data.local.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.local.entity.WorkoutSetEntity

@Database(entities = [
    WorkoutEntity::class,
    WorkoutSetEntity::class,
    ActivityEntity::class], version = 1)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutSetDao(): WorkoutSetDao
    abstract fun activityDao(): ActivityDao
}