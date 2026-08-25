package com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class TimerUseCase(
   dispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _elapsedSeconds = MutableStateFlow(0L)
    val elapsedSeconds: StateFlow<Long> = _elapsedSeconds.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    val timerFlow = flow {
        while (true) {
            if (_isRunning.value && !_isPaused.value) {
                _elapsedSeconds.value += 1
                emit(_elapsedSeconds.value)
                delay(1000L.milliseconds)
            } else {
                delay(100L.milliseconds)
            }
        }
    }.flowOn(dispatcher)

    init {
        scope.launch {
            timerFlow.collect {}
        }
    }

    fun start() {
        if (_isRunning.value && !_isPaused.value) return

        if (_isPaused.value) {
            _isPaused.value = false
        } else {
            _elapsedSeconds.value = 0L
            _isRunning.value = true
        }
    }

    fun pause() {
        if (_isRunning.value) {
            _isPaused.value = true
        }
    }

    fun resume() {
        if (_isRunning.value && _isPaused.value) {
            _isPaused.value = false
        }
    }

    fun stop() {
        _isRunning.value = false
        _isPaused.value = false
        _elapsedSeconds.value = 0L
    }
}
