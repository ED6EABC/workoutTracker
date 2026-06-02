package com.eelizarraras.workout.di

import androidx.room.Room
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
import com.eelizarraras.workout.core.data.model.dao.ActivityDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutDao
import com.eelizarraras.workout.core.data.model.dao.WorkoutSetDao
import com.eelizarraras.workout.core.data.repository.DataBaseRepositoryImpl
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import org.koin.dsl.module

val databaseModule = module {
    // Provides a single instance of database
    single {
        Room.databaseBuilder(
            context = get(),
            klass = WorkoutDatabase::class.java,
            "workout-database"
        ).build()
    }

    // Provides Dao's
    single<WorkoutDao> { get<WorkoutDatabase>().workoutDao() }
    single<WorkoutSetDao> { get<WorkoutDatabase>().workoutSetDao() }
    single<ActivityDao> { get<WorkoutDatabase>().activityDao() }

    // Provides repository
    single<DataBaseRepository> {
        DataBaseRepositoryImpl(
            workoutDatabase = get(),
            workoutDao = get(),
            workoutSetDao = get(),
            activityDao = get()
        )
    }
}