package com.eelizarraras.workout.flows.routine.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.eelizarraras.workout.flows.routine.presentation.components.RoutineActionCard
import com.eelizarraras.workout.ui.theme.BlueCardBackground
import com.eelizarraras.workout.ui.theme.CardContentColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun Routine(
    viewModel: NavigationViewModel = koinViewModel()
) {
    Content(
        onNavigate = {},
        onMenuClick = {},
        onProfileClick = {}
    )
}

@Preview
@Composable
private fun RoutinePreview() {
    Content(
        onNavigate = {},
        onMenuClick = {},
        onProfileClick = {}
    )
}

@Composable
private fun Content(
    onNavigate: (Screen) -> Unit,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopBar(
                onMenuClick = onMenuClick,
                onProfileClick = onProfileClick,
                title = stringResource(R.string.dashboard_title)
            )
        },
        floatingActionButton = {
            Card(
                colors = CardDefaults.cardColors(
                    contentColor = CardContentColor,
                    containerColor = BlueCardBackground
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_routine)
                    )
                    Text(
                        stringResource(R.string.new_workout),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(16.dp)
        ) {
            SectionHeader(
                title = stringResource(R.string.workouts_saved),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.keep_working),
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                color = CardContentColor.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoutineActionCard(
                title = "Full body",
                duration = "46",
                exercisesCount = 5,
                exercisesLabel = stringResource(R.string.workouts),
                onPlayClick = {},
                onMoreClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            RoutineActionCard(
                title = "Full body",
                duration = "46",
                exercisesCount = 5,
                exercisesLabel = stringResource(R.string.workouts),
                onPlayClick = {},
                onMoreClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            RoutineActionCard(
                title = "Full body",
                duration = "46",
                exercisesCount = 5,
                exercisesLabel = stringResource(R.string.workouts),
                onPlayClick = {},
                onMoreClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}