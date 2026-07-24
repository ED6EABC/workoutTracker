package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils

private const val WEIGHT_REGEX = "^(0|[1-9]\\d{0,2})\\d*(\\.\\d?)?"

internal fun String.isNotValidName(): Boolean {
    val regex = Regex("^[a-zA-Z\\s]+$")
    return !this.contains(regex) || this.isBlank()
}

internal fun String.removeNotValidCharactersToReps(): String {
    val justNumbers = this.filter { it.isDigit() }
    return justNumbers.replace(Regex("^0(\\d)"), "$1").take(2)
}

internal fun String.isNotValidWeightOrReps(): Boolean {
    return this in listOf("0", "00") || this.isEmpty()
}

internal fun String.removeNotValidCharactersToWeight(): String {
    val removeZero = this.replace(Regex("^0+(?=\\d)"), "")
    val regex = Regex(WEIGHT_REGEX)

    val matchResult = regex.find(removeZero)

    return if (matchResult != null) {
        val enteros = matchResult.groupValues[1]
        val decimales = matchResult.groupValues[2]
        enteros + decimales
    } else {
        ""
    }
}

internal fun String.toSeconds(): Int? {
    if (this.isBlank()) return null
    val parts = this.split(":")
    return if (parts.size == 2) {
        val minutes = parts[0].toIntOrNull() ?: 0
        val seconds = parts[1].toIntOrNull() ?: 0
        minutes * 60 + seconds
    } else {
        this.toIntOrNull()
    }
}

internal fun Int?.toRestTimeString(): String {
    if (this == null || this == 0) return ""
    val minutes = this / 60
    val seconds = this % 60
    return String.format(java.util.Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

internal fun String.formatRestTime(): String {
    val digits = this.filter { it.isDigit() }
    if (digits.isEmpty()) return ""

    val padded = digits.padStart(4, '0')
    val last4 = padded.takeLast(4)
    return "${last4.take(2)}:${last4.drop(2)}"
}