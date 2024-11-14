package com.recyclens.history.presentation

import com.recyclens.core.domain.history.HistoryWaste
import com.recyclens.history.presentation.util.HistoryByDate
import java.time.LocalDate

data class HistoryState(
    val loadedHistory: Boolean = false,
    val history: HistoryByDate = emptyMap(),
    val expandedDates: List<LocalDate> = listOf(),
    val historyWasteToRemove: HistoryWaste? = null,
)