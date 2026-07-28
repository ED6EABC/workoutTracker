package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Preview
@Composable
private fun DropDownCustomPreView() {
    WorkoutTrackerTheme {
        DropDownTimer(Modifier.height(80.dp)) {}
    }
}

@Composable
internal fun DropDownTimer(
    modifier: Modifier,
    value: List<String> = listOf("00","00"),
    onValueChange: (String) -> Unit
) {
    var minutes by remember { mutableStateOf(value.first()) }
    var seconds by remember { mutableStateOf(value.last()) }

    Row(modifier = modifier.padding(horizontal = 8.dp)) {
        DropDownCustom(
            istOfValues = (1..10).toList().map { it.toString() },
            value = minutes,
            onValueChange = {
                minutes = it
                onValueChange("$minutes:$seconds")
            }
        )
        Text(
            text = ":",
            color = Color.White.copy(alpha = 0.3f),
            autoSize = TextAutoSize.StepBased()
        )
        DropDownCustom(
            istOfValues = (10..50 step 10).toList().map { it.toString() },
            value = seconds,
            onValueChange = {
                seconds = it
                onValueChange("$minutes:$seconds")
            }
        )
    }
}

@Composable
private fun DropDownCustom(
    istOfValues: List<String> = listOf(),
    value: String = "",
    placeHolder: String = "00",
    onValueChange: (String) -> Unit
) {

    var isExpanded by remember { mutableStateOf(false) }

    val textColor: Color
    val textLabel: String

    if(value.isNotEmpty()) {
        textColor = Color.White
        textLabel = value.padStart(2, '0')
    } else {
        textColor = Color.White.copy(alpha = 0.3f)
        textLabel = placeHolder
    }

    Box {
        Text(
            text = textLabel,
            color = textColor,
            modifier = Modifier.clickable { isExpanded = true },
            autoSize = TextAutoSize.StepBased()
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            istOfValues.forEach { newValue ->
                DropdownMenuItem(
                    text = { Text(newValue) },
                    onClick = {
                        onValueChange(newValue)
                        isExpanded = false
                    }
                )
            }
        }
    }
}