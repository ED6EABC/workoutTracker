package com.eelizarraras.workout.flows.dashboard.presentation.viewModel

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.flows.dashboard.domine.use_cases.GetResentRoutinesUseCase
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEffect
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardEvent
import com.eelizarraras.workout.flows.dashboard.presentation.model.DashboardState
import com.eelizarraras.workout.flows.dashboard.presentation.model.LastRoutineDone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

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

    private fun Long.toMinutes(): Int {
        return (this / 60).toInt()
    }

    private fun Long.getWeekDayName(): String {
        val calendar = Calendar.getInstance().apply { timeInMillis = this@getWeekDayName }
        val format = SimpleDateFormat("EEEE", Locale.getDefault())
        val weekDayName = format.format(calendar.time)
        return weekDayName.replaceFirstChar { it.uppercase() }
    }

    private fun loadResentRoutines() {
        viewModelScope.launch(dispatcher) {
            _uiEffect.emit(DashboardEffect.ShowLoading(true))

            getResentRoutinesUseCase.invoke().collect { routines ->

                val lastRoutineDone = LastRoutineDone(
                    name = routines.first().routine.name,
                    weekDayName = routines.first().duration.getWeekDayName(),
                    duration = routines.first().duration.toMinutes().toString()
                )

                val topFiveRoutines =  routines.map { routine ->
                    RoutineModel(
                        id = routine.routine.id,
                        name = routine.routine.name,
                        workouts =  routine.routine.workouts.size,
                        durationInMinutes = routine.duration.toMinutes().toString()
                    )
                }

                _uiState.update {
                    it.copy(
                        lastRoutineDone = lastRoutineDone,
                        topFiveRoutines = topFiveRoutines
                    )
                }
            }
            _uiEffect.emit(DashboardEffect.ShowLoading(false))
        }
    }

}