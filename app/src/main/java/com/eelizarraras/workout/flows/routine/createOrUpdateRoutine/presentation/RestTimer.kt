package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Preview
@Composable
private fun RestTimerPreviewTitle() {
    WorkoutTrackerTheme {
        RestTimer(
            isTitle = true,
            height = 70.dp,
            onValueChange = {},
            onSwitchChange = {}
        )
    }
}

@Preview
@Composable
private fun RestTimerPreview() {
    WorkoutTrackerTheme {
        RestTimer(
            isTitle = false,
            height = 50.dp,
            onValueChange = {},
            onSwitchChange = {}
        )
    }
}

@Composable
internal fun RestTimer(
    timerValue: String = "00:00",
    isTitle: Boolean,
    height: Dp = 50.dp,
    isChecked: Boolean = false,
    onValueChange: (String) -> Unit,
    onSwitchChange: (Boolean) -> Unit = {}
) {
    var style: TextStyle
    var color: Color
    var fontWeight: FontWeight = FontWeight.Normal
    val label = stringResource(R.string.rest_time_label)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if(isTitle) {

            style = MaterialTheme.typography.labelLarge
            color = Color(0xFFC4D1FF)

            Column {
                Text(
                    text = label,
                    style = style,
                    color = color,
                    fontWeight = fontWeight
                )
                Spacer(modifier = Modifier.width(16.dp))

                DropDownTimer(
                    modifier = Modifier.height(height),
                    value = timerValue.split(":"),
                    onValueChange = onValueChange
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.set_timer_global),
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic,
                    color = Color.White.copy(alpha = 0.6f)
                )

                Switch(
                    checked = isChecked,
                    onCheckedChange = {
                        onSwitchChange(it)
                    }
                )
            }
        } else {

            style = MaterialTheme.typography.labelSmall
            color = Color.White.copy(alpha = 0.6f)
            fontWeight = FontWeight.Bold

            Text(
                text = label,
                style = style,
                color = color,
                fontWeight = fontWeight
            )

            DropDownTimer(
                modifier = Modifier.height(height),
                value = timerValue.split(":"),
                onValueChange = onValueChange
            )
        }
    }
}