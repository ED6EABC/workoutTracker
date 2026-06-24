package com.eelizarraras.workout.core.presentation.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.eelizarraras.workout.core.domine.model.BottomBarScreen
import com.eelizarraras.workout.core.domine.model.Screen
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class NavigationViewModel: ViewModel() {
    private var _backstack = mutableStateListOf<Screen>(Screen.Hub)
    val backStack = _backstack

    private var _bottomBackstack = mutableStateListOf<BottomBarScreen>(BottomBarScreen.Dashboard)
    val bottomBackstack = _bottomBackstack

    fun onNavigate(screen: Screen) {
        _backstack.add(screen)
    }

    fun switchBottomTab(destination: BottomBarScreen) {
        _bottomBackstack.clear()
        _bottomBackstack.add(destination)
    }

}