package com.eelizarraras.workout.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.presentation.model.WarningCardModel
import com.eelizarraras.workout.ui.theme.CardContentColor
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Preview
@Composable
private fun WarningCardPreview() {
    WorkoutTrackerTheme {
        Scaffold { _ ->
            WarningCard(
                model = WarningCardModel(
                    tittle = stringResource(R.string.play_routine_alert_title),
                    support =  stringResource(R.string.play_routine_alert_description),
                    confirmButtonLabel = stringResource(R.string.accept_label),
                    dismissButtonLabel = stringResource(R.string.cancel_label),
                    onConfirm = {},
                    onDismiss = {}
                )
            )
        }
    }
}

@Composable
fun WarningCard(
    model: WarningCardModel
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = {},
        title = { Text(model.tittle) },
        text = { Text(model.support) },
        confirmButton = {
            TextButton(
                onClick = model.onConfirm
            ) {
                Text(model.confirmButtonLabel, color = CardContentColor) }
            },
        dismissButton = {
            TextButton(
                onClick = model.onDismiss
            ) {
                Text(model.dismissButtonLabel, color = CardContentColor) }
        },
        containerColor = Color(0xFF35241E),
        titleContentColor = CardContentColor,
        textContentColor = CardContentColor,
    )
}