package com.eelizarraras.workout.core.presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.eelizarraras.workout.core.domine.model.BottomBarScreen
import com.eelizarraras.workout.core.presentation.components.MainNavBar
import com.eelizarraras.workout.core.presentation.components.MainTopBar
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel

@Composable
fun DashboardHub(
    viewModel: NavigationViewModel
) {
    var itemSelected: BottomBarScreen by remember { mutableStateOf(BottomBarScreen.Dashboard)}

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
                        itemSelected = screen
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