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

class RestTimerUseCase(
    dispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    val timerFlow = flow {
        while (true) {
            if (_isRunning.value) {
                if (_remainingSeconds.value > 0) {
                    delay(1000L.milliseconds)
                    _remainingSeconds.value -= 1
                    emit(_remainingSeconds.value)
                } else {
                    _isRunning.value = false
                    delay(100L.milliseconds)
                }
            } else {
                delay(100L.milliseconds) // Small delay to avoid busy loop
            }
        }
    }.flowOn(dispatcher)

    init {
        scope.launch {
            timerFlow.collect {}
        }
    }

    fun start(seconds: Int) {
        if (seconds <= 0) return
        _remainingSeconds.value = seconds
        _isRunning.value = true
    }

    fun stop() {
        _isRunning.value = false
        _remainingSeconds.value = 0
    }
}
