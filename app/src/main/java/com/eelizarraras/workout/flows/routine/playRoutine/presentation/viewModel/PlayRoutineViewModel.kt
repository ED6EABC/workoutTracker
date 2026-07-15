package com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.use_cases.GetRoutineUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.SaveRecordUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.TimerUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEffect
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.RoutineDetailState
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.Workout
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.WorkoutSetWithCheck
import com.eelizarraras.workout.flows.routine.seeRoutines.model.mappers.toRoutineDetailState
import java.util.Locale
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class PlayRoutineViewModel(
    private val getRoutineUseCase: GetRoutineUseCase,
    private val timerUseCase: TimerUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(RoutineDetailState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<PlayRoutineEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observeTimer()
    }

    private fun observeTimer() {
        viewModelScope.launch {
            timerUseCase.elapsedSeconds.collectLatest { seconds ->
                _uiState.update { it.copy(timer = formatSeconds(seconds)) }
            }
        }
        viewModelScope.launch {
            timerUseCase.isRunning.collectLatest { isRunning ->
                _uiState.update { it.copy(isStarted = isRunning) }
            }
        }
        viewModelScope.launch {
            timerUseCase.isPaused.collectLatest { isPaused ->
                _uiState.update { it.copy(isPaused = isPaused) }
            }
        }
        // Start the timer flow
        viewModelScope.launch {
            timerUseCase.timerFlow.collect()
        }
    }

    fun onEvent(event: PlayRoutineEvent) {
        when(event) {
            is PlayRoutineEvent.LoadRoutine -> loadRoutine(event.routineId)
            PlayRoutineEvent.StartRoutine -> timerUseCase.start()
            PlayRoutineEvent.PauseRoutine -> timerUseCase.pause()
            PlayRoutineEvent.ResumeRoutine -> timerUseCase.resume()
            PlayRoutineEvent.EndRoutine -> endRoutine()
            is PlayRoutineEvent.SetChecked -> setChecked(event.workoutId, event.setId, event.isChecked)
            is PlayRoutineEvent.MoveWorkout -> moveWorkout(event.fromIndex, event.toIndex)
            PlayRoutineEvent.ShowEndRoutineConfirmation ->showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        viewModelScope.launch {
            _effect.emit(PlayRoutineEffect.ShowConfirmationDialog)
        }
    }

    private fun moveWorkout(fromIndex: Int, toIndex: Int) {
        _uiState.update { state ->
            val newList = state.workouts.toMutableList().apply {
                add(toIndex, removeAt(fromIndex))
            }
            state.copy(workouts = newList)
        }
    }

    private fun loadRoutine(routineId: Long) {
        viewModelScope.launch(dispatcher) {
            _effect.emit(PlayRoutineEffect.ShowLoading(true))
            getRoutineUseCase.invoke(routineId).collect { routine ->
                _uiState.update { routine.toRoutineDetailState() }
            }
            _effect.emit(PlayRoutineEffect.ShowLoading(false))
        }
    }

    private fun endRoutine() {
        viewModelScope.launch {
            _effect.emit(PlayRoutineEffect.ShowLoading(true))

            val duration = timerUseCase.elapsedSeconds.value
            timerUseCase.stop()
            // TODO handle error case when the useCase can't save the record
            saveRecordUseCase.invoke(
                duration = duration,
                routineId = uiState.value.routineId
            )

            _effect.emit(PlayRoutineEffect.ShowLoading(false))
        }
    }

    // TODO move this function to an utils file
    private fun formatSeconds(totalSeconds: Long): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
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

    private fun RoutineDetailState.onUpdateSetContent(
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
