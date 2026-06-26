package com.eelizarraras.workout.flows.dashboard.domine.utils

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