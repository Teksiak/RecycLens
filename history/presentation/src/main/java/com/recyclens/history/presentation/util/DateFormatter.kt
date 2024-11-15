package com.recyclens.history.presentation.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.formatToHistoryDate(locale: Locale): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", locale)
    return this.format(formatter)
}
