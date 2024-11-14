package com.recyclens.history.presentation

import com.recyclens.core.domain.history.HistoryWaste

data class HistoryState(
    val loadedHistory: Boolean = false,
    val history: List<HistoryWaste> = emptyList(),
    val error: Throwable? = null
)