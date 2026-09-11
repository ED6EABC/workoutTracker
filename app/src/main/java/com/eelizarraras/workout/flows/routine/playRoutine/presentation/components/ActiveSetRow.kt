package com.eelizarraras.workout.flows.routine.playRoutine.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.core.presentation.model.WorkoutSetToUpdate
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.PlayRoutineEvent
import com.eelizarraras.workout.flows.routine.playRoutine.presentation.model.WorkoutSetWithCheck
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun ActiveSetRow(
    workoutId: String,
    sets: List<WorkoutSetWithCheck>,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Content(
        workoutId = workoutId,
        sets = sets,
        onEvent = onEvent
    )
}

@Preview
@Composable
fun ActiveSetRowPreview() {
    WorkoutTrackerTheme {
        Content(
            workoutId = "1",
            sets = listOf(
                WorkoutSetWithCheck(
                    workoutSet = WorkoutSet(
                        uid = "1",
                        weight = "10.0",
                        workoutUnit = WorkoutUnit.Lbs,
                        reps = "20"
                    ),
                    isChecked = false,
                    updatedWorkoutSet = WorkoutSetToUpdate(
                        weight = "11.0",
                        reps = "21",
                        workoutUnit = WorkoutUnit.Kg
                    )
                ),
                WorkoutSetWithCheck(
                    workoutSet = WorkoutSet(
                        uid = "2",
                        weight = "10.0",
                        workoutUnit = WorkoutUnit.Lbs,
                        reps = "20"
                    ),
                    isChecked = true,
                    updatedWorkoutSet = WorkoutSetToUpdate()
                )
            )
        ) {}
    }
}

@Composable
private fun Content(
    workoutId: String,
    sets: List<WorkoutSetWithCheck>,
    onEvent: (PlayRoutineEvent) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.set),
                modifier = Modifier.weight(0.3f),
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
            Text(
                stringResource(R.string.weight_hint),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = TealAccent,
                fontSize = 12.sp
            )
            Text(
                stringResource(R.string.reps),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = TealAccent,
                fontSize = 12.sp
            )
            Icon(
                Icons.Default.DoneAll,
                null,
                modifier = Modifier.weight(0.3f),
                tint = Color.White.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        sets.forEachIndexed { index, workout ->
            val setNumber = index + 1
            SetRow(
                setNumber = setNumber,
                workout = workout,
                onCheckedChange = { isChecked ->
                    onEvent(PlayRoutineEvent.SetChecked(
                        workoutId = workoutId,
                        setId = workout.workoutSet.uid,
                        isChecked = isChecked,
                        weight = workout.workoutSet.weight,
                        reps = workout.workoutSet.reps,
                        workoutUnit = workout.workoutSet.workoutUnit
                    ))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
private fun SetRow(
    setNumber: Int,
    workout: WorkoutSetWithCheck,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (workout.isChecked) Color.White.copy(alpha = 0.05f) else Color.Transparent)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = setNumber.toString(),
            modifier = Modifier.width(40.dp).weight(0.5f),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProgressBox(
                baseValue = workout.workoutSet.weight,
                progressValue = workout.updatedWorkoutSet?.weight ?: ""
            )

            Spacer(modifier = Modifier.width(8.dp))

            SetUnit(
                previosUnit = workout.workoutSet.workoutUnit.toString(),
                newUnit = workout.updatedWorkoutSet?.workoutUnit?.toString() ?: ""
            )
        }

        ProgressBox(
            baseValue = workout.workoutSet.reps,
            progressValue = workout.updatedWorkoutSet?.reps ?: "",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        val isChecked = workout.isChecked
        Box(
            modifier = Modifier
                .weight(0.5f)
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) TealAccent else Color.Transparent)
                .border(
                    1.dp,
                    if (isChecked) Color.Transparent else Color.White.copy(alpha = 0.2f),
                    RoundedCornerShape(8.dp)
                )
                .clickable { onCheckedChange(!isChecked) }
        )
    }
}

@Composable
private fun ProgressBox(
    baseValue: String,
    progressValue: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {

        val isSetChange = progressValue.isNotEmpty()

        if(isSetChange) {
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_double_arrow),
                "Routine progress",
                modifier = Modifier.size(50.dp),
                tint = TealAccent.copy(alpha = 0.2f)
            )
        }

        Row( horizontalArrangement = Arrangement.spacedBy(8.dp) ) {
            Text(
                text = baseValue,
                color = Color.White.copy(alpha = 0.6f)
            )
            if(isSetChange) {
                Text(
                    text = progressValue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun SetUnit(
    previosUnit: String,
    newUnit: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(newUnit.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newUnit,
                textAlign = TextAlign.Center,
                color = TealAccent,
                fontSize = 14.sp
            )
        }
        Text(
            text = previosUnit,
            textAlign = TextAlign.Center,
            color = TealAccent,
            fontSize = if(newUnit.isNotEmpty()) 8.sp else 12.sp
        )
    }
}