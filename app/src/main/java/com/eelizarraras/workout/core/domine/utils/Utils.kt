package com.eelizarraras.workout.core.domine.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal fun Long.toMinutes(): Int {
    return (this / 60).toInt()
}

internal fun Long.getWeekDayName(): String {
    val calendar = Calendar.getInstance().apply { timeInMillis = this@getWeekDayName }
    val format = SimpleDateFormat("EEEE", Locale.getDefault())
    val weekDayName = format.format(calendar.time)
    return weekDayName.replaceFirstChar { it.uppercase() }
}

fun formatSeconds(totalSeconds: Long): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}
