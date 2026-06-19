package com.eelizarraras.workout.flows.routine.seeRoutines.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.model.Screen
import com.eelizarraras.workout.core.domine.use_cases.GetRoutinesOverviewUseCase
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerEffect
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerEvent
import com.eelizarraras.workout.flows.routine.seeRoutines.model.RoutineViewerState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class RoutineViewerViewModel(
    private val getRoutinesUseCase: GetRoutinesOverviewUseCase,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(RoutineViewerState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<RoutineViewerEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private var getRoutineJob: Job? = null

    init {
        handleEvent(RoutineViewerEvent.GetRoutines)
    }

    fun handleEvent(event: RoutineViewerEvent) {
        when(event) {
            is RoutineViewerEvent.AddRoutine -> {
                navigateTo(event.screen)
            }
            is RoutineViewerEvent.PlayRoutine -> {
                navigateTo(event.screen)
            }
            RoutineViewerEvent.GetRoutines -> {
                getRoutines()
            }
        }
    }

    private fun navigateTo(screen: Screen) {
        viewModelScope.launch() {
            _uiEffect.emit(RoutineViewerEffect.OnNavigateTo(screen))
        }
    }

    private fun getRoutines() {
        getRoutineJob?.cancel()
        getRoutineJob = viewModelScope.launch(ioDispatcher) {
            _uiEffect.emit(RoutineViewerEffect.ShowLoading(true))
            getRoutinesUseCase().collect { routines ->
                _uiState.update { state -> state.copy(routines = routines) }
                _uiEffect.emit(RoutineViewerEffect.ShowLoading(false))
            }
        }
    }

}