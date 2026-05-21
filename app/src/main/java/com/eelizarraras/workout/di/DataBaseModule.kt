package com.eelizarraras.workout.di

import androidx.room.Room
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
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
    single { get<WorkoutDatabase>().workoutDao() }
    single { get<WorkoutDatabase>().workoutSetDao() }
    single { get<WorkoutDatabase>().activityDao() }
}