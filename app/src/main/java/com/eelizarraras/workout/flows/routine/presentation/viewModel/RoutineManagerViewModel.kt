package com.eelizarraras.workout.flows.routine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.flows.routine.presentation.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.presentation.model.RoutineEffect
import com.eelizarraras.workout.flows.routine.presentation.model.RoutineIntent
import com.eelizarraras.workout.flows.routine.presentation.model.Workout
import com.eelizarraras.workout.flows.routine.presentation.model.WorkoutSet
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class RoutineManagerViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CreateRoutineState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<RoutineEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleIntent(intent: RoutineIntent) {
        when(intent) {
            RoutineIntent.AddWorkout -> addWorkout()
            is RoutineIntent.Save -> TODO()
            is RoutineIntent.SetName -> setName(intent.name)
            is RoutineIntent.AddSet -> addSetToWorkout(intent.workoutId)
            is RoutineIntent.UpdateSet -> {
                updateSet(
                    workoutId = intent.workoutId,
                    workoutSetId = intent.workoutSetId,
                    weight = intent.weight,
                    unit = intent.unit,
                    reps = intent.reps
                )
            }
            is RoutineIntent.DeleteWorkout -> deleteWorkout(intent.workoutId)
        }
    }

    private fun setName(name: String) {
        viewModelScope.launch { _uiState.update { it.copy(name = name) } }
    }

    private fun addWorkout() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(workouts = it.workouts + Workout())
            }
        }
    }

    private fun  CreateRoutineState.getWorkout(
        workoutId: String,
        onWorkout: (Workout) -> Workout
    ): List<Workout> {
        return this.workouts.map { workout ->
            if(workout.uid == workoutId) {
                onWorkout(workout)
            } else workout
        }
    }

    private fun  addSetToWorkout(workoutId: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(workouts = state.getWorkout(
                    workoutId = workoutId,
                    onWorkout = { workout ->
                        workout.copy(sets = workout.sets + WorkoutSet())
                    }
                ))
            }
        }
    }


    private fun  CreateRoutineState.getWorkoutSet(
        workoutId: String,
        workoutSetId: String,
        onSet: (WorkoutSet) -> WorkoutSet
    ): List<Workout> {

        return getWorkout(workoutId) { workout ->
            workout.copy(
                sets = workout.sets.map { set ->
                    if(set.uid == workoutSetId) {
                        onSet(set)
                    } else set
                }
            )
        }
    }

    private fun updateSet(
        workoutId: String,
        workoutSetId: String,
        weight: String? = null,
        unit: WorkoutUnit? = null,
        reps: String? = null
    ) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                   workouts = state.getWorkoutSet(workoutId, workoutSetId) { workoutSet ->
                       val weightValue = weight ?: workoutSet.weight
                       val unitValue = unit ?: workoutSet.workoutUnit
                       val repsValue = reps ?: workoutSet.reps

                       workoutSet.copy(weight = weightValue, workoutUnit = unitValue, reps = repsValue)
                   }
                )

            }
        }
    }

    private fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(workouts = state.workouts.filter { it.uid != workoutId } )
            }
        }
    }

}