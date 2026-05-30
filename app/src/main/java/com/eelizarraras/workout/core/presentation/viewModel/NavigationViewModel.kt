package com.eelizarraras.workout.core.presentation.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.eelizarraras.workout.core.domine.model.Screen
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class NavigationViewModel: ViewModel() {
    private var _backstack = mutableStateListOf<Screen>(Screen.Dashboard)
    val backStack = _backstack

    fun onNavigate(screen: Screen) {
        _backstack.add(screen)
    }

    fun onBack() {
        _backstack.removeLastOrNull()
    }

}