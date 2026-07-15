package com.eelizarraras.workout.di

import androidx.room.Room
import com.eelizarraras.workout.core.data.local.WorkoutDatabase
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
        )
        .fallbackToDestructiveMigration(false) // Adding this for the structural overhaul
        .build()
    }

    // Provides Dao's
    single { get<WorkoutDatabase>().exerciseDao() }
    single { get<WorkoutDatabase>().routineSetDao() }
    single { get<WorkoutDatabase>().routineExerciseDao() }
    single { get<WorkoutDatabase>().routineDao() }
    single { get<WorkoutDatabase>().workoutSessionDao() }
    single { get<WorkoutDatabase>().loggedExerciseDao() }
    single { get<WorkoutDatabase>().loggedSetDao() }

    // Provides repository
    single<DataBaseRepository> {
        DataBaseRepositoryImpl(
            workoutDatabase = get(),
            exerciseDao = get(),
            routineSetDao = get(),
            routineExerciseDao = get(),
            routineDao = get(),
            workoutSessionDao = get()
        )
    }
}