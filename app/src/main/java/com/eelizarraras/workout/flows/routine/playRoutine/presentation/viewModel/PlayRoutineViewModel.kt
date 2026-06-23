package com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.use_cases.GetRoutineUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEffect
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineState
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
            is PlayRoutineEvent.SetChecked -> TODO()
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

}