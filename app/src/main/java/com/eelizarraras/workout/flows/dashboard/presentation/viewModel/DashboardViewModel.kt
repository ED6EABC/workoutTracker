package com.eelizarraras.workout.flows.dashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.flows.dashboard.domine.use_cases.GetResentRoutinesUseCase
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEffect
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEvent
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardState
import com.eelizarraras.workout.flows.dashboard.presentation.model.mappers.toPresentation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class DashboardViewModel(
    private val getResentRoutinesUseCase: GetResentRoutinesUseCase,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DashboardEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        onEvent(DashboardEvent.LoadResentRoutines)
    }

    fun onEvent(event: DashboardEvent) {
        when(event) {
            DashboardEvent.LoadResentRoutines -> loadResentRoutines()
            is DashboardEvent.OnPlayRoutine -> TODO()
            DashboardEvent.SeeMore -> TODO()
        }
    }

    private fun loadResentRoutines() {
        viewModelScope.launch(dispatcher) {
            _uiEffect.emit(DashboardEffect.ShowLoading(true))

            getResentRoutinesUseCase.invoke().collect { records ->
                var lastRoutineDone: RoutineModel? = null
                val topFourRoutines: MutableList<RoutineModel> = mutableListOf()

                records.forEachIndexed { index, record -> run {
                    if (index == 0) {
                        lastRoutineDone = record.toPresentation()
                    } else {
                        topFourRoutines.add(record.toPresentation())
                    }
                }

                _uiState.update {
                    it.copy(
                        lastRoutineDone = lastRoutineDone,
                        topFiveRoutines = topFourRoutines
                    )
                }
            }
            _uiEffect.emit(DashboardEffect.ShowLoading(false))
        }
    }
    }

}