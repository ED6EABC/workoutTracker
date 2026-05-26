package com.eelizarraras.workout.flows.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.domine.model.Screen
import com.eelizarraras.workout.core.presentation.components.MainTopBar
import com.eelizarraras.workout.core.presentation.components.SectionHeader
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.dashboard.presentation.components.GreetingsCard
import com.eelizarraras.workout.flows.dashboard.presentation.components.LastWorkoutCard
import com.eelizarraras.workout.flows.dashboard.presentation.components.WorkoutCard
import com.eelizarraras.workout.ui.theme.BlueCardBackground
import com.eelizarraras.workout.ui.theme.CardContentColor
import com.eelizarraras.workout.ui.theme.LightningBoltColor
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun Dashboard(
    viewModel: NavigationViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    Content(
        onNavigate = { screen -> viewModel.onNavigate(screen) },
        onMenuClick = { /* TODO: Implementar menú */ },
        onProfileClick = { /* TODO: Navegar al perfil */ },
        onNewWorkoutClick = { /* TODO: Crear nueva rutina */ },
        newWorkoutLabel = stringResource(R.string.new_workout),
        lastWorkoutLabel = stringResource(R.string.last_workout),
        myWorkoutsLabel = stringResource(R.string.my_workouts),
        seeAllLabel = stringResource(R.string.see_all),
        workout1Title = stringResource(R.string.workout_hypertrophy_legs),
        workout2Title = stringResource(R.string.workout_push),
        workout3Title = stringResource(R.string.workout_cardio_hiit),
        modifier = Modifier.padding(paddingValues)
    )
}

@Preview
@Composable
private fun DashboardPreview() {
    WorkoutTrackerTheme {
        Content(
            onNavigate = {},
            onMenuClick = {},
            onProfileClick = {},
            onNewWorkoutClick = {},
            newWorkoutLabel = "Nueva Rutina",
            lastWorkoutLabel = "Última Rutina",
            myWorkoutsLabel = "Mis Rutinas",
            seeAllLabel = "Ver todo",
            workout1Title = "Hipertrofia Piernas",
            workout2Title = "Empuje (Push)",
            workout3Title = "Cardio HIIT"
        )
    }
}

@Composable
private fun Content(
    onNavigate: (Screen) -> Unit,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    onNewWorkoutClick: () -> Unit,
    newWorkoutLabel: String,
    lastWorkoutLabel: String,
    myWorkoutsLabel: String,
    seeAllLabel: String,
    workout1Title: String,
    workout2Title: String,
    workout3Title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212)) // Fondo oscuro profundo
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Tarjeta de Bienvenida
        GreetingsCard()

        // 2. Sección Última Rutina
        SectionHeader(title = lastWorkoutLabel)
        LastWorkoutCard()

        // 3. Sección Mis Rutinas con "Ver todo"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionHeader(title = myWorkoutsLabel)
            Text(
                text = seeAllLabel,
                style = MaterialTheme.typography.labelLarge,
                color = TealAccent,
                modifier = Modifier.clickable { /* Navegar a lista completa */ }
            )
        }

        // Lista de Rutinas
        WorkoutCard(
            title = workout1Title,
            exercisesCount = 6,
            durationMinutes = 60,
            onPlayClick = { /* Iniciar rutina */ }
        )

        WorkoutCard(
            title = workout2Title,
            exercisesCount = 5,
            durationMinutes = 50,
            onPlayClick = { /* Iniciar rutina */ }
        )

        WorkoutCard(
            title = workout3Title,
            exercisesCount = 4,
            durationMinutes = 30,
            onPlayClick = { /* Iniciar rutina */ }
        )

        // Espaciador final para que el FAB no tape el contenido
        Spacer(modifier = Modifier.height(80.dp))
    }
}