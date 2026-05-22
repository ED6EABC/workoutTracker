package com.eelizarraras.workout.flows.workout.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.domine.model.Screen
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.workout.data.model.WorkoutUI
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
    state: WorkoutUI? = null,
    onNavigate: (Screen) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Workout form
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Nombre")
                }
            )
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Descripcion")
                }
            )
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Nota")
                }
            )

            // Set form
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Series")
                IconButton(
                    onClick = {TODO()}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "Add set"
                    )
                }
            }

            var expanded by remember { mutableStateOf(false) }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Peso")
                TextField(
                    value = "",
                    onValueChange = {}
                )
                Text("Unidad")
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Option 1") },
                        onClick = { /* Do something... */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Option 2") },
                        onClick = { /* Do something... */ }
                    )
                }
            }

            TextButton(
                onClick = {}
            ) {
                Text("Guardar")
            }
        }
    }
}