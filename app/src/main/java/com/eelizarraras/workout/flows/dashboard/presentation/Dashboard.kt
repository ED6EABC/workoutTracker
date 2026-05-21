package com.eelizarraras.workout.flows.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eelizarraras.workout.core.data.model.Screen
import com.eelizarraras.workout.core.viewModel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Dashboard(
    viewModel: NavigationViewModel = koinViewModel()
) {
    Content { screen ->
        viewModel.onNavigate(screen)
    }
}

@Preview
@Composable
private fun DashboardPreview() {
    Content {}
}

@Composable
private fun Content(
    onNavigate: (Screen) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column {
            Text("Dashboard", modifier = Modifier.padding(paddingValues))
            TextButton(
                onClick = { onNavigate(Screen.Routine) }
            ) { Text("Navigate to Routine") }
        }
    }
}