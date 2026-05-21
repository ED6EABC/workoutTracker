package com.eelizarraras.workout.flows.workout.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eelizarraras.workout.commons.data.Screen
import com.eelizarraras.workout.commons.viewModel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Workout(
    viewModel: NavigationViewModel = koinViewModel()
) {
    Content { screen ->
        viewModel.onBack()
    }
}

@Preview
@Composable
private fun WorkoutPreview() {
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
            Text("Workout", modifier = Modifier.padding(paddingValues))
            TextButton(
                onClick = { onNavigate(Screen.Workout) }
            ) { Text("Navigate back") }
        }
    }
}