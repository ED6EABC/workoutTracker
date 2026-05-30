package com.eelizarraras.workout.flows.routine.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.eelizarraras.workout.core.presentation.model.RoutineModel
import com.eelizarraras.workout.core.presentation.viewModel.NavigationViewModel
import com.eelizarraras.workout.flows.routine.presentation.components.RoutineActionCard
import com.eelizarraras.workout.flows.routine.presentation.model.RoutineState
import com.eelizarraras.workout.ui.theme.BlueCardBackground
import com.eelizarraras.workout.ui.theme.CardContentColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun Routine(
    viewModel: NavigationViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    // TODO retrieve this from a viewModel
    val model = RoutineState(routines = listOf(
        RoutineModel(name = "Cardio HIIT", workouts = 6, duration = "50"),
        RoutineModel(name = "Hipertrofia Piernas", workouts = 5, duration = "60"),
        RoutineModel(name = "Empuje (Push)", workouts = 4, duration = "30")
    ))

    Content(
        paddingValues = paddingValues,
        RoutineState = model,
        onAddRoutine = {
            viewModel.onNavigate(Screen.AddRoutine)
        }
    )
}

@Preview
@Composable
private fun RoutinePreview() {
    Content(
        paddingValues = PaddingValues(0.dp),
        RoutineState = RoutineState(routines = listOf(
            RoutineModel(name = "Cardio HIIT", workouts = 6, duration = "50"),
            RoutineModel(name = "Hipertrofia Piernas", workouts = 5, duration = "60"),
            RoutineModel(name = "Empuje (Push)", workouts = 4, duration = "30")
        )),
        onAddRoutine = {}
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    RoutineState: RoutineState,
    onAddRoutine: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddRoutine,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(paddingValues)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
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
            }
            items(
                items = RoutineState.routines,
                key = { it.name +  it.workouts + it.duration }
            ) { routine ->
                RoutineActionCard(
                    title = routine.name,
                    duration = routine.duration,
                    exercisesCount = routine.workouts,
                    onPlayClick = {},
                    onMoreClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
