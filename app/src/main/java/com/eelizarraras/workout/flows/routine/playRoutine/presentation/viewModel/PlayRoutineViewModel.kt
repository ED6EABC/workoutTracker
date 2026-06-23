package com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.use_cases.GetRoutineUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEffect
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineState
import com.eelizarraras.workout.flows.routine.playRoutine.model.Workout
import com.eelizarraras.workout.flows.routine.playRoutine.model.WorkoutSetWithCheck
import com.eelizarraras.workout.flows.routine.seeRoutines.model.mappers.toPlayRoutineState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class PlayRoutineViewModel(
    private val getRoutineUseCase: GetRoutineUseCase,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(PlayRoutineState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<PlayRoutineEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: PlayRoutineEvent) {
        when(event) {
            is PlayRoutineEvent.LoadRoutine -> loadRoutine(event.routineId)
            PlayRoutineEvent.EndRoutine -> TODO()
            PlayRoutineEvent.PauseRoutine -> TODO()
            is PlayRoutineEvent.SetChecked -> setChecked(event.workoutId, event.setId, event.isChecked)
            PlayRoutineEvent.StartRoutine -> TODO()
            PlayRoutineEvent.ResumeRoutine -> TODO()
        }
    }

    private fun loadRoutine(routineId: Long) {
        viewModelScope.launch(dispatcher) {
            getRoutineUseCase.invoke(routineId).collect { routine ->
                _uiState.update { routine.toPlayRoutineState() }
            }
        }
    }

    private fun WorkoutSetWithCheck.onUpdateSetContent(
        setId: String,
        onUpdate: (WorkoutSetWithCheck) -> WorkoutSetWithCheck
    ): WorkoutSetWithCheck {
        return if(this.workoutSet.uid == setId) {
            onUpdate(this)
        } else this
    }

    private fun Workout.onUpdate(
        workoutId: String,
        onWorkout: (Workout) -> Workout
    ): Workout {
        return if (this.id == workoutId) {
            onWorkout(this)
        } else this
    }

    private fun PlayRoutineState.onUpdateSetContent(
        workoutId: String,
        setId: String,
        onUpdate: (WorkoutSetWithCheck) -> WorkoutSetWithCheck
    ): List<Workout> {
        return this.workouts.map { workout ->
            workout.onUpdate(workoutId) {
                val sets = workout.sets.map { set ->
                    set.onUpdateSetContent(setId, onUpdate)
                }
                workout.copy(sets = sets)
            }
        }
    }

    private fun setChecked(workoutId: String, setId: String, isChecked: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                val workoutsUpdated = it.onUpdateSetContent(workoutId, setId) { set ->
                    set.copy(isChecked = isChecked)
                }

                it.copy(workouts = workoutsUpdated)
            }
        }
    }
}