package com.eelizarraras.workout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.eelizarraras.workout.core.domine.model.Screen
import com.eelizarraras.workout.core.presentation.components.MainNavBar
import com.eelizarraras.workout.core.presentation.components.MainTopBar
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.core.presentation.views.NavigationGraph
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: NavigationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            var itemSelected: Screen by remember { mutableStateOf(Screen.Dashboard)}

            WorkoutTrackerTheme() {
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
                                    viewModel.onNavigate(screen)
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
        }
    }
}