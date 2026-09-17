package com.eelizarraras.workout.flows.progress.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.data.model.entity.view.LoggedSetWithDetailsTuple
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.domine.repository.DataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

data class ProgressState(
    val totalRoutines: Int = 0,
    val weeklyActivityMinutes: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0), // L, M, X, J, V, S, D
    val personalRecords: List<LoggedSetWithDetailsTuple> = emptyList(),
    val selectedUnit: WorkoutUnit = WorkoutUnit.Kg
)

class ProgressViewModel(
    private val repository: DataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _selectedUnit = MutableStateFlow(WorkoutUnit.Kg)
    val selectedUnit = _selectedUnit.asStateFlow()

    private val _uiState = MutableStateFlow(ProgressState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProgressData()
    }

    fun selectUnit(unit: WorkoutUnit) {
        _selectedUnit.value = unit
    }

    private fun loadProgressData() {
        viewModelScope.launch(ioDispatcher) {
            combine(
                repository.getRoutinesOverview(),
                repository.getAllSessions(),
                repository.getAllLoggedSetsWithDetails(),
                _selectedUnit
            ) { routines, sessions, loggedSets, unit ->
                
                val totalRoutines = routines.size

                val weeklyMinutes = MutableList(7) { 0 }
                val now = Calendar.getInstance()
                val currentWeek = now.get(Calendar.WEEK_OF_YEAR)
                val currentYear = now.get(Calendar.YEAR)

                val sessionCal = Calendar.getInstance()
                sessions.forEach { session ->
                    sessionCal.timeInMillis = session.date
                    if (sessionCal.get(Calendar.WEEK_OF_YEAR) == currentWeek && sessionCal.get(Calendar.YEAR) == currentYear) {
                        val dayOfWeek = sessionCal.get(Calendar.DAY_OF_WEEK)
                        val index = when (dayOfWeek) {
                            Calendar.MONDAY -> 0
                            Calendar.TUESDAY -> 1
                            Calendar.WEDNESDAY -> 2
                            Calendar.THURSDAY -> 3
                            Calendar.FRIDAY -> 4
                            Calendar.SATURDAY -> 5
                            Calendar.SUNDAY -> 6
                            else -> -1
                        }
                        if (index in 0..6) {
                            weeklyMinutes[index] += (session.duration / 60).toInt()
                        }
                    }
                }

                val filteredSets = loggedSets.filter { it.unit == unit }
                val maxSetsPerExercise = filteredSets.groupBy { it.exerciseName }
                    .mapValues { (_, sets) ->
                        sets.maxByOrNull { it.weight }
                    }
                    .values
                    .filterNotNull()
                    .sortedByDescending { it.weight }
                    .take(5)

                ProgressState(
                    totalRoutines = totalRoutines,
                    weeklyActivityMinutes = weeklyMinutes,
                    personalRecords = maxSetsPerExercise,
                    selectedUnit = unit
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
