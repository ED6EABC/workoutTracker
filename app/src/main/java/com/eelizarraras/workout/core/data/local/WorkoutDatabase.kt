package com.eelizarraras.workout.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.RecordDao
import com.eelizarraras.workout.core.data.model.dao.RoutineDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.RecordEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity

@Database(entities = [
    WorkoutEntity::class,
    WorkoutSetEntity::class,
    ActivityEntity::class,
    RoutineEntity::class,
    RecordEntity::class],
    version = 1,
    //autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutSetDao(): WorkoutSetDao
    abstract fun activityDao(): ActivityDao
    abstract fun routineDao(): RoutineDao
    abstract fun recordDao(): RecordDao
}