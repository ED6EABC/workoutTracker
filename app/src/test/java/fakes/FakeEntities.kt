package fakes

import com.eelizarraras.workout.core.data.model.entity.ActivityEntity
import com.eelizarraras.workout.core.data.model.entity.RoutineEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutEntity
import com.eelizarraras.workout.core.data.model.entity.WorkoutSetEntity
import com.eelizarraras.workout.core.domine.model.ActivityModel
import com.eelizarraras.workout.core.domine.model.WorkoutModel
import com.eelizarraras.workout.core.domine.model.WorkoutSetModel
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun getWorkouts(): List<WorkoutEntity> {
    return listOf(
        WorkoutEntity(
            uid = 1,
            name = "Pres de banco plano",
            description = "Un ejercicio...",
            note = "Hacer el ejercicio con ganas",
        ),
        WorkoutEntity(
            uid = 2,
            name = "Sentadillas",
            description = "Un ejercicio...",
            note = "Hacer el ejercicio con ganas",
        )
    )
}

fun getWorkoutsWithOptionalParameters(): List<WorkoutEntity> {
    return listOf(
        WorkoutEntity(
            uid = 1,
            name = "Pres de banco plano",
            description = null,
            note = "Hacer el ejercicio con ganas",
        ),
        WorkoutEntity(
            uid = 2,
            name = "Sentadillas",
            description = "Un ejercicio...",
            note = null
        )
    )
}

fun getWorkoutModelWithOptionalParameters(): WorkoutModel {
    return WorkoutModel(id = 1, name = "Pres de banca", description = null, note = null)
}

fun getWorkoutSet(): WorkoutSetEntity {
    return WorkoutSetEntity(
        uid = 1,
        weight = 80.0,
        workoutUnit = WorkoutUnit.Kg,
        reps = 15,
    )
}

fun getActivityEntity(): ActivityEntity {
    return ActivityEntity(
        uid = 1L,
        routineId = 4L,
        workoutId = 2L,
        setId = 3L,
    )
}

fun getWorkoutEntity(): WorkoutEntity {
    return WorkoutEntity(uid = 1L, name = "Test", description = null, note = null)
}

fun getWorkoutSetModel(unit: WorkoutUnit = WorkoutUnit.Lbs): WorkoutSetModel {
    return WorkoutSetModel(id = 1, weight = 10.0, workoutUnit = unit, reps = 10)
}

fun getWorkoutSetEntity(): WorkoutSetEntity {
    return WorkoutSetEntity(uid = 1, weight = 10.0, workoutUnit = WorkoutUnit.Kg, reps = 10)
}

fun getActivityModel(): ActivityModel {
    return ActivityModel(
        id = 1,
        routineId = 4L,
        workoutId = 2,
        setId = 3
    )
}

fun getWorkoutModel(): WorkoutModel {
    return WorkoutModel(
        id = 1,
        name = "Pres de banca",
        description = "Hazlo con ganas",
        note = "Una nota"
    )
}

fun getWorkoutModelList(): List<WorkoutModel> {
    return listOf(
        WorkoutModel(
            id = 1,
            name = "Pres de banca",
            description = "Hazlo con ganas",
            note = "Una nota"
        ),
        WorkoutModel(
            id = 2,
            name = "Sentadilla",
            description = "Hazlo con ganas x2",
            note = "Una nota x2"
        )
    )
}

fun getWorkoutSetModelList(): List<List<WorkoutSetModel>> {
    return listOf(
        listOf(
            WorkoutSetModel(id = 1, weight = 10.0, workoutUnit = WorkoutUnit.Kg, reps = 10),
            WorkoutSetModel(id = 2, weight = 10.0, workoutUnit = WorkoutUnit.Kg, reps = 10)
        ),
        listOf(
            WorkoutSetModel(id = 3, weight = 10.0, workoutUnit = WorkoutUnit.Kg, reps = 10),
            WorkoutSetModel(id = 4, weight = 10.0, workoutUnit = WorkoutUnit.Kg, reps = 10)
        )
    )
}

fun getRoutinesEntity(): Flow<Array<RoutineEntity>> {
    return flowOf(arrayOf(
        RoutineEntity(
            uid = 1L,
            name = "Lunes de pecho"
        ),
        RoutineEntity(
            uid = 2L,
            name = "Martes de pierna"
        )
    ))
}