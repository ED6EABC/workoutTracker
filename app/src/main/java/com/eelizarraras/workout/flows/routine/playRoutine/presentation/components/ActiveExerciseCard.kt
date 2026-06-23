package com.eelizarraras.workout.flows.routine.playRoutine.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.model.WorkoutSetWithCheck
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun ActiveExerciseCard(
    name: String,
    setsInfo: String,
    sets: List<WorkoutSetWithCheck>,
    modifier: Modifier = Modifier,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Content(
        name = name,
        setsInfo = setsInfo,
        sets = sets,
        modifier = modifier,
        onEvent = onEvent
    )
}

@Preview
@Composable
private fun ActiveExerciseCardPreview() {
    WorkoutTrackerTheme {
        Content(
            name = "Pres de banco",
            setsInfo = "3",
            sets = listOf(
                WorkoutSetWithCheck(
                    workoutSet = WorkoutSet(
                        uid = "1",
                        weight = "10.0",
                        workoutUnit = WorkoutUnit.Lbs,
                        reps = "20"
                    ),
                    isChecked = false
                ),
                WorkoutSetWithCheck(
                    workoutSet = WorkoutSet(
                        uid = "2",
                        weight = "10.0",
                        workoutUnit = WorkoutUnit.Lbs,
                        reps = "20"
                    ),
                    isChecked = true
                )
            ),
            modifier = Modifier,
            onEvent = { }
        )
    }
}

@Composable
private fun Content(
    name: String,
    setsInfo: String,
    sets: List<WorkoutSetWithCheck>,
    modifier: Modifier = Modifier,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreyCardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFC4D1FF),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { isExpanded = !isExpanded },
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color(0xFFC4D1FF)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.6f)
                )
            }
            Text(
                text = stringResource(R.string.sets, setsInfo),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.6f)
            )

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))

                // Header Table
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        stringResource(R.string.set),
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                    Text(
                        stringResource(R.string.unit_label),
                        textAlign = TextAlign.Center,
                        color = TealAccent,
                        fontSize = 12.sp
                    )
                    Text(
                        stringResource(R.string.reps),
                        textAlign = TextAlign.Center,
                        color = TealAccent,
                        fontSize = 12.sp
                    )
                    Icon(
                        Icons.Default.DoneAll,
                        null,
                        tint = Color.White.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ActiveSetRow(
                    sets = sets,
                    onEvent = onEvent
                )
            }
        }
    }
}