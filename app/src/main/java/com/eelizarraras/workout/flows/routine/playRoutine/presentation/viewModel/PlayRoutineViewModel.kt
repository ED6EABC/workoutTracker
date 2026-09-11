package com.eelizarraras.workout.flows.routine.playRoutine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
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
import com.eelizarraras.workout.core.domine.utils.formatSeconds
import com.eelizarraras.workout.core.presentation.model.WorkoutSetToUpdate
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.toRestTimeString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        observeProgress()
    }

    private fun observeProgress() {
        viewModelScope.launch {
            uiState.collectLatest { state ->
                timerUseCase.updateProgress(
                    done = state.doneWorkouts.size,
                    total = state.workoutsTotal
                )
            }
        }
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
    }

    fun onEvent(event: PlayRoutineEvent) {
        when(event) {
            is PlayRoutineEvent.LoadRoutine -> loadRoutine(event.routineId)
            PlayRoutineEvent.StartRoutine -> {
                timerUseCase.start()
                startService()
            }
            PlayRoutineEvent.PauseRoutine -> timerUseCase.pause()
            PlayRoutineEvent.ResumeRoutine -> timerUseCase.resume()
            PlayRoutineEvent.EndRoutine -> endRoutine()
            is PlayRoutineEvent.SetChecked -> {
                setChecked(
                    event.workoutId,
                    event.setId,
                    event.isChecked,
                    event.weight,
                    event.reps,
                    event.workoutUnit
                )
            }
            is PlayRoutineEvent.MoveWorkout -> moveWorkout(event.fromIndex, event.toIndex)
            is PlayRoutineEvent.MoveWorkoutToDone -> moveWorkoutToDone(event.workoutId)
            is PlayRoutineEvent.MoveWorkoutToTodo -> moveWorkoutToTodo(event.workoutId)
            PlayRoutineEvent.ShowEndRoutineConfirmation -> showConfirmationDialog()
            PlayRoutineEvent.SkipRest -> restTimerUseCase.stop()
            is PlayRoutineEvent.SetUpdatedSet -> onSetUpdated(event.workoutSet)
        }
    }

    private fun startService() {
        viewModelScope.launch {
            _effect.emit(PlayRoutineEffect.StartService)
        }
    }

    private fun showConfirmationDialog() {
        viewModelScope.launch {
            _effect.emit(PlayRoutineEffect.ShowConfirmationDialog)
        }
    }

    private fun moveWorkout(fromIndex: Int, toIndex: Int) {
        _uiState.update { state ->
            val todoSize = state.todoWorkouts.size
            val doneSize = state.doneWorkouts.size

            val todoIndices = 1..todoSize
            val doneIndices = (todoSize + 2)..(todoSize + doneSize + 1)

            val fromInTodo = fromIndex in todoIndices
            val fromInDone = fromIndex in doneIndices

            val newTodo = state.todoWorkouts.toMutableList()
            val newDone = state.doneWorkouts.toMutableList()

            when {
                // Moving Todo -> Done
                fromInTodo && toIndex >= (todoSize + 1) -> {
                    val item = newTodo.removeAt(fromIndex - 1)
                    val updatedItem = item.copy(
                        sets = item.sets.map { it.copy(isChecked = true) }
                    )
                    val targetIndex = (toIndex - (todoSize + 2)).coerceIn(0, newDone.size)
                    newDone.add(targetIndex, updatedItem)
                }
                // Moving Done -> Todo
                fromInDone && toIndex <= (todoSize + 1) -> {
                    val item = newDone.removeAt(fromIndex - (todoSize + 2))
                    val updatedItem = item.copy(
                        sets = item.sets.map { it.copy(isChecked = false) }
                    )
                    val targetIndex = (toIndex - 1).coerceIn(0, newTodo.size)
                    newTodo.add(targetIndex, updatedItem)
                }
                // Within Todo
                fromInTodo && toIndex in todoIndices -> {
                    val item = newTodo.removeAt(fromIndex - 1)
                    val targetIndex = (toIndex - 1).coerceIn(0, newTodo.size)
                    newTodo.add(targetIndex, item)
                }
                // Within Done
                fromInDone && toIndex in doneIndices -> {
                    val item = newDone.removeAt(fromIndex - (todoSize + 2))
                    val targetIndex = (toIndex - (todoSize + 2)).coerceIn(0, newDone.size)
                    newDone.add(targetIndex, item)
                }
                else -> return@update state
            }

            state.copy(todoWorkouts = newTodo, doneWorkouts = newDone)
        }
    }

    private fun moveWorkoutToDone(workoutId: String) {
        _uiState.update { state ->
            val workout = state.todoWorkouts.find { it.id == workoutId } ?: return@update state
            val updatedWorkout = workout.copy(
                sets = workout.sets.map { it.copy(isChecked = true) }
            )
            val newTodo = state.todoWorkouts.filter { it.id != workoutId }
            val newDone = state.doneWorkouts.toMutableList().apply {
                add(updatedWorkout)
            }
            state.copy(todoWorkouts = newTodo, doneWorkouts = newDone)
        }
    }

    private fun moveWorkoutToTodo(workoutId: String) {
        _uiState.update { state ->
            val workout = state.doneWorkouts.find { it.id == workoutId } ?: return@update state
            val updatedWorkout = workout.copy(
                sets = workout.sets.map { it.copy(isChecked = false) }
            )
            val newDone = state.doneWorkouts.filter { it.id != workoutId }
            val newTodo = state.todoWorkouts.toMutableList().apply {
                add(updatedWorkout)
            }
            state.copy(todoWorkouts = newTodo, doneWorkouts = newDone)
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
            _effect.emit(PlayRoutineEffect.StopService)

            _effect.emit(PlayRoutineEffect.ShowLoading(false))
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

    private fun List<Workout>.updateWorkoutSet(
        workoutId: String,
        setId: String,
        onUpdate: (WorkoutSetWithCheck) -> WorkoutSetWithCheck
    ): List<Workout> {
        return this.map { workout ->
            workout.onUpdate(workoutId) {
                val sets = workout.sets.map { set ->
                    set.onUpdateSetContent(setId, onUpdate)
                }
                workout.copy(sets = sets)
            }
        }
    }

    private fun onSetUpdated(workoutSetToUpdate: WorkoutSetToUpdate?) {
        viewModelScope.launch {
            _uiState.update { state ->
                if(workoutSetToUpdate == null) {
                    state.copy(currentWorkoutSet = null)
                } else {
                    val todoWorkouts = state.todoWorkouts.updateWorkoutSet(
                        workoutSetToUpdate.workoutId,
                        workoutSetToUpdate.setId
                    ) { set -> set.copy(updatedWorkoutSet = workoutSetToUpdate) }

                    val doneWorkouts = state.doneWorkouts.updateWorkoutSet(
                        workoutSetToUpdate.workoutId,
                        workoutSetToUpdate.setId
                    ) { set -> set.copy(updatedWorkoutSet = workoutSetToUpdate) }

                    state.copy(
                        todoWorkouts = todoWorkouts,
                        doneWorkouts = doneWorkouts,
                        currentWorkoutSet = null
                    )
                }
            }
        }
    }

    private fun setChecked(
        workoutId: String,
        setId: String,
        isChecked: Boolean,
        weight: String,
        reps: String,
        workoutUnit: WorkoutUnit
    ) {
        viewModelScope.launch {
            var restTime: Int? = null
            _uiState.update { state ->
                val todoWorkouts = state.todoWorkouts.updateWorkoutSet(workoutId, setId) { set ->
                    set.copy(isChecked = isChecked)
                }
                val doneWorkouts = state.doneWorkouts.updateWorkoutSet(workoutId, setId) { set ->
                    set.copy(isChecked = isChecked)
                }

                val workout = todoWorkouts.find { it.id == workoutId } ?: doneWorkouts.find { it.id == workoutId }

                if (isChecked) {
                    restTime = workout?.restTimeInSeconds ?: state.defaultRestTimeInSeconds
                }

                state.validateIfWorkoutIsCompleted(todoWorkouts, doneWorkouts, workout).copy(
                    currentWorkoutSet = if (isChecked) WorkoutSetToUpdate(
                        workoutId = workoutId,
                        setId = setId,
                        weight = weight,
                        workoutUnit = workoutUnit,
                        reps = reps,
                    ) else null
                )
            }

            if (isChecked) {
                restTime?.let {
                    restTimerUseCase.start(it)
                    startService()
                }
            } else {
                restTimerUseCase.stop()
            }
        }
    }

    private fun RoutineDetailState.validateIfWorkoutIsCompleted(
        todoWorkoutsUpdated: List<Workout>,
        doneWorkoutsUpdated: List<Workout>,
        workout: Workout?
    ): RoutineDetailState {
        val workoutInTodo = todoWorkoutsUpdated.find { it.id == workout?.id }
        val workoutInDone = doneWorkoutsUpdated.find { it.id == workout?.id }

        val updatedWorkout = workoutInTodo ?: workoutInDone ?: return this.copy(
            todoWorkouts = todoWorkoutsUpdated,
            doneWorkouts = doneWorkoutsUpdated
        )

        val isCompleted = updatedWorkout.sets.all { it.isChecked }

        return when {
            isCompleted && workoutInTodo != null -> {
                val newTodo = todoWorkoutsUpdated.filter { it.id != updatedWorkout.id }
                val newDone = doneWorkoutsUpdated.toMutableList().apply {
                    if (none { it.id == updatedWorkout.id }) {
                        add(updatedWorkout)
                    }
                }
                this.copy(todoWorkouts = newTodo, doneWorkouts = newDone)
            }

            !isCompleted && workoutInDone != null -> {
                val newDone = doneWorkoutsUpdated.filter { it.id != updatedWorkout.id }
                val newTodo = todoWorkoutsUpdated.toMutableList().apply {
                    if (none { it.id == updatedWorkout.id }) {
                        add(updatedWorkout)
                    }
                }
                this.copy(todoWorkouts = newTodo, doneWorkouts = newDone)
            }

            else -> {
                this.copy(todoWorkouts = todoWorkoutsUpdated, doneWorkouts = doneWorkoutsUpdated)
            }
        }
    }
}
