package com.eelizarraras.workout.flows.routine.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.eelizarraras.workout.core.presentation.components.SectionHeader
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.routine.presentation.components.RoutineActionCard
import com.eelizarraras.workout.ui.theme.BlueCardBackground
import com.eelizarraras.workout.ui.theme.CardContentColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun Routine(
    viewModel: NavigationViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    Content(
        onNavigate = { screen -> viewModel.onNavigate(screen) },
        onMenuClick = {},
        onProfileClick = {},
        paddingValues = paddingValues
    )
}

@Preview
@Composable
private fun RoutinePreview() {
    Content(
        onNavigate = {},
        onMenuClick = {},
        onProfileClick = {},
        paddingValues = PaddingValues(0.dp)
    )
}

@Composable
private fun Content(
    onNavigate: (Screen) -> Unit,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    paddingValues: PaddingValues
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                shape = RoundedCornerShape(16.dp),
                containerColor = BlueCardBackground,
                contentColor = CardContentColor,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_routine)
                    )
                },
                text = {
                    Text(
                        stringResource(R.string.new_workout),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(paddingValues)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
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
