package com.eelizarraras.workout.flows.routine.playRoutine.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.flows.routine.playRoutine.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.model.WorkoutSetWithCheck
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun ActiveSetRow(
    sets: List<WorkoutSetWithCheck>,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Content(
        sets = sets,
        onEvent = onEvent
    )
}

@Preview
@Composable
fun ActiveSetRowPreview() {
    WorkoutTrackerTheme {
        Content(
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
            )
        ) {}
    }
}

@Composable
private fun Content(
    sets: List<WorkoutSetWithCheck>,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Column {
        sets.forEachIndexed { index, workout ->
            val setNumber = index + 1
            SetRow(
                setNumber = setNumber,
                weight = workout.workoutSet.weight,
                reps = workout.workoutSet.reps,
                isChecked = workout.isChecked,
                onCheckedChange = { isChecked ->
                    onEvent(PlayRoutineEvent.SetChecked(uid = workout.workoutSet.uid, isChecked))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
private fun SetRow(
    setNumber: Int,
    weight: String,
    reps: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isChecked) Color.White.copy(alpha = 0.05f) else Color.Transparent)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setNumber.toString(),
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = weight, color = Color.White.copy(alpha = 0.4f))
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = reps, color = Color.White.copy(alpha = 0.4f))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) TealAccent else Color.Transparent)
                .border(
                    1.dp,
                    if (isChecked) Color.Transparent else Color.White.copy(alpha = 0.2f),
                    RoundedCornerShape(8.dp)
                )
                .clickable { onCheckedChange(isChecked) }
        )
    }
}