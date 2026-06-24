package com.eelizarraras.workout.core.presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.eelizarraras.workout.core.presentation.components.MainNavBar
import com.eelizarraras.workout.core.presentation.components.MainTopBar
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel

@Composable
fun DashboardHub(
    viewModel: NavigationViewModel
) {
    val itemSelected = viewModel.bottomBackstack.last()

    Scaffold(
        topBar = {
            MainTopBar(
                onMenuClick = {},
                onProfileClick = {},
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(itemSelected.topBarLabel)
            )
        },
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                MainNavBar(
                    currentScreen = itemSelected,
                    onNavigate = { screen ->
                        viewModel.switchBottomTab(screen)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        containerColor = Color(0xFF121212),
    ) { paddingValues ->
        NavigationGraph(viewModel, paddingValues)
    }
}