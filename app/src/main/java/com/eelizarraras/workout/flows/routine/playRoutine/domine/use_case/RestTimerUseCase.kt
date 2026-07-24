package com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.Duration.Companion.milliseconds

class RestTimerUseCase(
    private val dispatcher: CoroutineDispatcher
) {
    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    fun start(seconds: Int) {
        if (seconds <= 0) return
        _remainingSeconds.value = seconds
        _isRunning.value = true
    }

    fun stop() {
        _isRunning.value = false
        _remainingSeconds.value = 0
    }

    val timerFlow = flow {
        while (true) {
            if (_isRunning.value) {
                if (_remainingSeconds.value > 0) {
                    delay(1000L.milliseconds)
                    _remainingSeconds.value -= 1
                    emit(_remainingSeconds.value)
                } else {
                    _isRunning.value = false
                }
            } else {
                delay(100L.milliseconds) // Small delay to avoid busy loop
            }
        }
    }.flowOn(dispatcher)
}