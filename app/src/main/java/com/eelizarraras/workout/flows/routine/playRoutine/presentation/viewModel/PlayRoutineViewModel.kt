package com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.use_cases.GetRoutineUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.RestTimerUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.SaveRecordUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.TimerUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEffect
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.RoutineDetailState
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.Workout
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.WorkoutSetWithCheck
import com.eelizarraras.workout.flows.routine.seeRoutines.model.mappers.toRoutineDetailState
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.toRestTimeString
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
    private val restTimerUseCase: RestTimerUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(RoutineDetailState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<PlayRoutineEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observeTimer()
        observeRestTimer()
    }

    private fun observeRestTimer() {
        viewModelScope.launch {
            restTimerUseCase.remainingSeconds.collectLatest { seconds ->
                _uiState.update { it.copy(restTimer = seconds.toRestTimeString()) }
            }
        }
        viewModelScope.launch {
            restTimerUseCase.isRunning.collectLatest { isRunning ->
                _uiState.update { it.copy(isResting = isRunning) }
            }
        }
        viewModelScope.launch {
            restTimerUseCase.timerFlow.collect()
        }
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
            PlayRoutineEvent.ShowEndRoutineConfirmation -> showConfirmationDialog()
            PlayRoutineEvent.SkipRest -> restTimerUseCase.stop()
        }
    }

    private fun showConfirmationDialog() {
        viewModelScope.launch {
            _effect.emit(PlayRoutineEffect.ShowConfirmationDialog)
        }
    }

    private fun moveWorkout(fromIndex: Int, toIndex: Int) {
        _uiState.update { state ->
            val newList = state.todoWorkouts.toMutableList().apply {
                add(toIndex, removeAt(fromIndex))
            }
            state.copy(todoWorkouts = newList)
        }
    }

    private fun loadRoutine(routineId: Long) {
        viewModelScope.launch(dispatcher) {
            _effect.emit(PlayRoutineEffect.ShowLoading(true))
            _uiState.update { getRoutineUseCase.invoke(routineId).toRoutineDetailState() }
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
                name = uiState.value.routineName,
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
        return this.todoWorkouts.map { workout ->
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
            _uiState.update { state ->
                val todoWorkouts = state.onUpdateSetContent(workoutId, setId) { set ->
                    set.copy(isChecked = isChecked)
                }

                val workout = todoWorkouts.find { it.id == workoutId }

                if (isChecked) {
                    val restTime = workout?.restTimeInSeconds ?: state.defaultRestTimeInSeconds
                    restTime?.let { restTimerUseCase.start(it) }
                } else {
                    restTimerUseCase.stop()
                }

                state.validateIfWorkoutIsCompleted(todoWorkouts, state.doneWorkouts, workout)
            }
        }
    }

    private fun RoutineDetailState.validateIfWorkoutIsCompleted(
        todoWorkoutsUpdated: List<Workout>,
        doneWorkouts: List<Workout>,
        workout: Workout?
    ): RoutineDetailState {
        val updatedWorkout = todoWorkoutsUpdated.find { it.id == workout?.id }
        val isCompleted = updatedWorkout?.sets?.all { it.isChecked } ?: false

        return if (isCompleted) {
            val newTodo = todoWorkoutsUpdated.filter { it.id != updatedWorkout.id }
            doneWorkouts.toMutableList()
            val newDone = doneWorkouts.toMutableList().apply {
                if (none { it.id == updatedWorkout.id }) {
                    add(updatedWorkout)
                }
            }
            this.copy(todoWorkouts = newTodo, doneWorkouts = newDone)
        } else {
            this.copy(todoWorkouts = todoWorkoutsUpdated, doneWorkouts = doneWorkouts)
        }
    }
}
