package com.eelizarraras.workout.flows.routine.seeRoutines.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.model.Screen
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerEffect
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerEvent
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class RoutineViewerViewModel(

): ViewModel() {

    private val _uiState = MutableStateFlow(RoutineViewerState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<RoutineViewerEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: RoutineViewerEvent) {
        when(event) {
            is RoutineViewerEvent.AddRoutine -> {
                navigateTo(event.screen)
            }
            RoutineViewerEvent.PlayRoutine -> {
                //TODO
            }
        }
    }

    private fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _uiEffect.emit(RoutineViewerEffect.OnNavigateTo(screen))
        }
    }

}