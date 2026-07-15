package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.domine.use_cases.GetRoutineUseCase
import com.eelizarraras.workout.core.domine.use_cases.SaveRoutineUseCase
import com.eelizarraras.workout.core.domine.use_cases.UpdateRoutineUseCase
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.RoutineEffect
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.RoutineEvent
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.Workout
import com.eelizarraras.workout.flows.routine.seeRoutines.model.mappers.toCreateRoutineState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class RoutineManagerViewModel(
    private val saveRoutineUseCase: SaveRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val getRoutineUseCase: GetRoutineUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateRoutineState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<RoutineEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private var _isUpdating = false

    fun onEvent(intent: RoutineEvent) {
        when(intent) {
            RoutineEvent.AddWorkout -> addWorkout()
            is RoutineEvent.Save -> save(intent.routine)
            is RoutineEvent.SetName -> setName(intent.name)
            is RoutineEvent.AddSet -> addSetToWorkout(intent.workoutId)
            is RoutineEvent.UpdateSet -> {
                updateSet(
                    workoutId = intent.workoutId,
                    workoutSetId = intent.workoutSetId,
                    weight = intent.weight,
                    unit = intent.unit,
                    reps = intent.reps
                )
            }
            is RoutineEvent.DeleteWorkout -> deleteWorkout(intent.workoutId)
            is RoutineEvent.SetWorkoutName -> setWorkoutName(intent.workoutId, intent.name)
            is RoutineEvent.LoadRoutineToUpdate -> loadRoutineToUpdate(intent.routineId)
            RoutineEvent.ResetToInitialState -> resetToInitialState()
        }
    }

    private fun resetToInitialState() {
        viewModelScope.launch {
            _uiEffect.emit(RoutineEffect.ShowLoading(true))
            _uiState.update { CreateRoutineState() }
            _isUpdating = false
            _uiEffect.emit(RoutineEffect.ShowLoading(false))
        }
    }

    private fun save(routine: CreateRoutineState) {
        viewModelScope.launch {
            _uiEffect.emit(RoutineEffect.ShowLoading(true))
            //TODO validate if the routine is success
            // Otherwise show an error and keep the data
            if (_isUpdating) {
                updateRoutineUseCase(routine)
            } else {
                saveRoutineUseCase(routine)
            }
            clearState()
            _uiEffect.emit(RoutineEffect.ShowLoading(false))
        }
    }

    private fun clearState() {
        _uiState.update { CreateRoutineState() }
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

    private fun setWorkoutName(workoutId: String, name: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(workouts = state.getWorkout(workoutId) { workout ->
                    workout.copy(name = name)
                })
            }
        }
    }

    private fun loadRoutineToUpdate(routineId: Long?) {
        if(routineId == null) return
        _isUpdating = true

        viewModelScope.launch {
            _uiEffect.emit(RoutineEffect.ShowLoading(true))
            getRoutineUseCase(routineId).collect { routine ->
                _uiState.update { routine.toCreateRoutineState() }
            }
            _uiEffect.emit(RoutineEffect.ShowLoading(false))
        }
    }
}