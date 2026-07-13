package com.eelizarraras.workout.core.presentation.model

data class WarningCardModel(
    val tittle: String,
    val support: String,
    val confirmButtonLabel: String,
    val dismissButtonLabel: String,
    val onConfirm: () -> Unit,
    val onDismiss: () -> Unit,
)
