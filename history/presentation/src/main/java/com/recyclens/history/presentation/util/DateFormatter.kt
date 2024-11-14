package com.recyclens.history.presentation.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.formatToHistoryDate(languageCode: String = "pl"): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale(languageCode))
    return this.format(formatter)
}
