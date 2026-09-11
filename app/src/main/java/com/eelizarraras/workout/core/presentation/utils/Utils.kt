package com.eelizarraras.workout.core.presentation.utils
private const val WEIGHT_REGEX = "^(0|[1-9]\\d{0,2})\\d*(\\.\\d?)?"

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

internal fun String.removeNotValidCharactersToReps(): String {
    val justNumbers = this.filter { it.isDigit() }
    return justNumbers.replace(Regex("^0(\\d)"), "$1").take(2)
}