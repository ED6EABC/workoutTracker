package com.eelizarraras.workout.flows.routine.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Preview
@Composable
private fun InputBoxPreview() {
    WorkoutTrackerTheme {
        InputBox(
            placeholder = "Peso",
            value = "",
            onValueChange = {},
            showUnits = true,
            keyboardType = KeyboardType.Decimal
        )
    }
}

@Composable
internal fun InputBox(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    showUnits: Boolean = false,
    keyboardType: KeyboardType
) {

    var isExpanded by remember { mutableStateOf(false) }
    var unitSelected by remember { mutableStateOf(WorkoutUnit.Kg) }

    Column {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E1E1E)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeholder,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                },
                trailingIcon = {
                    if (showUnits) {
                        TextButton(
                            onClick = { isExpanded = !isExpanded },
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text(unitSelected.name)
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1E1E1E),
                    unfocusedContainerColor = Color(0xFF1E1E1E)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                keyboardActions = KeyboardActions(
                    onDone = {  }
                )
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            WorkoutUnit.entries.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) },
                    onClick = {
                        unitSelected = unit
                        isExpanded = false
                    }
                )
            }
        }
    }
}