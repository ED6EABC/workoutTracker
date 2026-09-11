package com.eelizarraras.workout.flows.routine.playRoutine.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.presentation.model.WorkoutSetToUpdate
import com.eelizarraras.workout.core.presentation.utils.removeNotValidCharactersToReps
import com.eelizarraras.workout.core.presentation.utils.removeNotValidCharactersToWeight
import com.eelizarraras.workout.flows.routine.components.UnitsDropDown
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import kotlin.text.isEmpty

@Preview
@Composable
fun OnSetDoneDialogPreview() {
    WorkoutTrackerTheme {
        Content(
            workoutSet = WorkoutSetToUpdate(
                workoutId = "1",
                setId = "1",
                reps = "10",
                weight = "10",
                workoutUnit = WorkoutUnit.Lbs
            )
        ) { }
    }
}

@Composable
internal fun OnSetDoneDialog(
    workoutSet: WorkoutSetToUpdate,
    onClick: (WorkoutSetToUpdate?) -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Content(
            workoutSet = workoutSet,
            onClick = onClick
        )
    }
}

@Composable
private fun Content(
    workoutSet: WorkoutSetToUpdate,
    onClick: (WorkoutSetToUpdate?) -> Unit
) {

    var weight by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf(workoutSet.workoutUnit) }

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = DarkGreyCardBackground,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 2.dp,
            color = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Resultado",
                modifier = Modifier.wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(16.dp))

            SetRow(
                value = weight,
                placeHolder = workoutSet.weight,
                label = stringResource(R.string.weight_label),
                onValueChange = { weight = it.removeNotValidCharactersToWeight() }
            )
            Spacer(Modifier.height(8.dp))
            SetRow(
                value = reps,
                placeHolder = workoutSet.reps,
                label = stringResource(R.string.reps),
                onValueChange = { reps = it.removeNotValidCharactersToReps() }
            )
            Spacer(Modifier.height(8.dp))


            unit?.name?.let { name ->
                UnitsDropDown(
                    modifier = Modifier,
                    isExpanded = isExpanded,
                    onDismissRequest = { state -> isExpanded = state },
                    onValueChange = { unitSelected -> unit = unitSelected }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.unit_label),
                            textAlign = TextAlign.Center,
                        )

                        TextButton(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = name,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    onClick(workoutSet.validate(weight, reps, unit))
                },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = stringResource(R.string.accept_label),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun WorkoutSetToUpdate.validate(
    weight: String,
    reps: String,
     unit: WorkoutUnit?
): WorkoutSetToUpdate? {
    return if(weight.isNotEmpty() && reps.isNotEmpty() && weight != this.weight && reps != this.reps) {
        this.copy(
            weight = weight.ifEmpty { this.weight },
            reps = reps.ifEmpty { this.reps },
            workoutUnit = unit
        )
    } else {
        return null
    }
}

@Composable
private fun SetRow(
    value: String,
    placeHolder: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
        )

        val style = TextStyle(
            color = Color.White,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        val placeHolderStyle = TextStyle(
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.width(100.dp),
            cursorBrush = SolidColor(Color.Green),
            textStyle = style,
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color.Gray.copy(alpha = 0.2f))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeHolder,
                            style = placeHolderStyle
                        )
                    } else {
                        innerTextField()
                    }
                }
            }
        )
    }
}