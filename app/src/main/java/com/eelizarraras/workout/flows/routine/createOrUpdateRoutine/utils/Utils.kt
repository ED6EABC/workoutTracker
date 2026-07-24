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