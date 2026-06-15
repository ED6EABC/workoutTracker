package fakes

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.WorkoutUnit

fun getAWorkoutWithoutOptionalParameters(): WorkoutEntity {
    return WorkoutEntity(
        uid = 2,
        name = "Pres de banco",
        description = null,
        note = null
    )
}

fun getAWorkout(): WorkoutEntity {
    return WorkoutEntity(
        uid = 1,
        name = "Pres banca plano",
        description = "Happy training",
        note = "Something"
    )
}

fun getAWorkoutList(): Array<WorkoutEntity> {
    return arrayOf(
        WorkoutEntity(
            uid = 1,
            name = "Pres banca plano",
            description = "Happy training",
            note = "Something"
        ),
        WorkoutEntity(
            uid = 2,
            name = "Remo con polea",
            description = "Happy training",
            note = "Something"
        )
    )
}

fun getAWorkoutSet(unit: WorkoutUnit = WorkoutUnit.Kg): WorkoutSetEntity {
    return WorkoutSetEntity(
        uid = 2,
        weight = 10.0,
        workoutUnit = unit,
        reps = 3
    )
}

fun getAWorkoutSetList(): Array<WorkoutSetEntity> {
    return arrayOf(
        WorkoutSetEntity(
            uid = 1,
            weight = 2.0,
            workoutUnit = WorkoutUnit.Kg,
            reps = 3
        ),
        WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Lbs,
            reps = 6
        )
    )
}

fun getWorkoutSetsWithDifferentUnits(): List<WorkoutSetEntity> {
    return listOf(
        WorkoutSetEntity(
            uid = 2,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Kg,
            reps = 3
        ),
        WorkoutSetEntity(
            uid = 3,
            weight = 10.0,
            workoutUnit = WorkoutUnit.Plates,
            reps = 3
        )
    )
}

fun getAnActivity(
    routineId: Long,
    workoutId: Long,
    workSetId: Long,
    uid: Long = 6L,
): ActivityEntity {
    return ActivityEntity(
        uid = uid,
        routineId = routineId,
        workoutId = workoutId,
        setId = workSetId
    )
}

fun getRoutine(): RoutineEntity {
    return RoutineEntity(
        uid = 8L,
        name = "Destruccion de pecho"
    )
}