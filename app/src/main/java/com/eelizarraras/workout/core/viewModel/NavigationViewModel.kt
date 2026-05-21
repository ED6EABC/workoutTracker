package com.eelizarraras.workout.core.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.eelizarraras.workout.core.data.model.Screen

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