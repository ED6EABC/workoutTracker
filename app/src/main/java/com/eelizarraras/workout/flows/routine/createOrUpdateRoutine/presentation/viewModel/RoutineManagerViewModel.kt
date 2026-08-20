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
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.formatRestTime
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.isNotValidName
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.isNotValidWeightOrReps
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.removeNotValidCharactersToReps
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.removeNotValidCharactersToWeight
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

    private var _routine: CreateRoutineState? = null

    fun onEvent(intent: RoutineEvent) {
        when(intent) {
            RoutineEvent.AddWorkout -> addWorkout()
            is RoutineEvent.Save -> save(intent.routine)
            is RoutineEvent.SetName -> setName(intent.name)
            is RoutineEvent.AddSet -> addSetToWorkout(intent.workoutId)
            is RoutineEvent.DeleteSet -> deleteSet(intent.workoutId, intent.setId)
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
            is RoutineEvent.SetRoutineRestTime -> setRoutineRestTime(intent.restTime)
            is RoutineEvent.SetWorkoutRestTime -> setWorkoutRestTime(intent.workoutId, intent.restTime)
            is RoutineEvent.ResetToInitialState -> resetToInitialState(intent.routineId)
            is RoutineEvent.ShowConfirmation -> showConfirmationDialog(intent.isNavigationBack)
            is RoutineEvent.OnRestSwitchChange -> onSwitchChange(intent.isCheck)
            RoutineEvent.ValidateFields -> validateFields()
        }
    }

    private fun validateFields() {
        var isValid = true
        if(uiState.value.isNameError || uiState.value.name.isEmpty()) isValid = false
        if(uiState.value.workouts.isEmpty()) isValid = false

        uiState.value.workouts.forEach {
            if(it.isNameError || it.name.isEmpty()) isValid = false
            if(it.sets.isEmpty()) isValid = false
            it.sets.forEach { set ->
                if(set.isWeightError || set.weight.isEmpty() || set.isRepsError || set.reps.isEmpty()) isValid = false
            }
        }

        if (isValid && (!uiState.value.isUpdating || validateIfThereIsAnyUpdatedAnyField())) {
            showConfirmationDialog(false)
        }
    }

    private fun validateIfThereIsAnyUpdatedAnyField(): Boolean {
        return _routine != _uiState.value
    }

    private fun showConfirmationDialog(navigationBack: Boolean) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isNavigationBack = navigationBack) }
            _uiEffect.emit(RoutineEffect.ShowConfirmationDialog)
        }
    }

    private fun resetToInitialState(routineId: Long? = null) {
        viewModelScope.launch {
            _uiEffect.emit(RoutineEffect.ShowLoading(true))
            _uiState.update { CreateRoutineState() }
            routineId?.let { id ->
                val routine = getRoutineUseCase(id)
                routine.toCreateRoutineState().let { loadedRoutine ->
                    _routine = loadedRoutine
                    getUpdateScope { loadedRoutine }
                }
            }
            _uiEffect.emit(RoutineEffect.ShowLoading(false))
        }
    }

    private fun save(routine: CreateRoutineState) {
        viewModelScope.launch {
            _uiEffect.emit(RoutineEffect.ShowLoading(true))
            //TODO validate if the routine is success
            // Otherwise show an error and keep the data
            if (_uiState.value.isUpdating) {
                updateRoutineUseCase(routine)
            } else {
                saveRoutineUseCase(routine)
            }
            showAnimation()
            _uiEffect.emit(RoutineEffect.ShowLoading(false))
        }
    }

    private fun showAnimation() {
        getUpdateScope { state ->
            state.copy(showAnimation = true)
        }
    }

    private fun getUpdateScope(
        onUpdate: (CreateRoutineState) -> CreateRoutineState
    ) {
        _uiState.update { onUpdate(it) }
    }

    private fun setName(name: String) {
        getUpdateScope { it.copy(name = name, isNameError = name.isNotValidName()) }
    }

    private fun addWorkout() {
        getUpdateScope {
            it.copy(workouts = it.workouts + Workout())
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
        getUpdateScope { state ->
            state.copy(workouts = state.getWorkout(
                workoutId = workoutId,
                onWorkout = { workout ->
                    workout.copy(sets = workout.sets + WorkoutSet())
                }
            ))
        }
    }

    private fun deleteSet(workoutId: String, setId: String) {
        getUpdateScope { state ->
            state.copy(workouts = state.getWorkout(
                workoutId = workoutId,
                onWorkout = { workout ->
                    val filtered = workout.sets.filter { it.uid != setId }
                    workout.copy(sets = filtered)
                }
            ))
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
        getUpdateScope { state ->
            state.copy(
               workouts = state.getWorkoutSet(workoutId, workoutSetId) { workoutSet ->
                   val weightValue = weight ?: workoutSet.weight
                   val unitValue = unit ?: workoutSet.workoutUnit
                   val repsValue = reps ?: workoutSet.reps

                   workoutSet.copy(
                       weight = weightValue.removeNotValidCharactersToWeight(),
                       isWeightError = weightValue.isNotValidWeightOrReps(),
                       workoutUnit = unitValue,
                       reps = repsValue.removeNotValidCharactersToReps(),
                       isRepsError = repsValue.isNotValidWeightOrReps()
                   )
               }
            )
        }
    }

    private fun deleteWorkout(workoutId: String) {
        getUpdateScope { state ->
            state.copy(workouts = state.workouts.filter { it.uid != workoutId } )
        }
    }

    private fun setWorkoutName(workoutId: String, name: String) {
        getUpdateScope { state ->
            state.copy(workouts = state.getWorkout(workoutId) { workout ->
                workout.copy(name = name, isNameError = name.isNotValidName())
            })
        }
    }

    private fun setRoutineRestTime(restTime: String) {
        getUpdateScope { state ->
            state.copy(restTime = restTime.formatRestTime())
        }
    }

    private fun setWorkoutRestTime(workoutId: String, restTime: String) {
        getUpdateScope { state ->
            state.copy(workouts = state.getWorkout(workoutId) { workout ->
                workout.copy(restTime = restTime.formatRestTime())
            })
        }
    }

    private fun onSwitchChange(isCheck: Boolean) {
        getUpdateScope { state ->
            state.copy(isRestSwitchChecked = isCheck)
        }
    }
}