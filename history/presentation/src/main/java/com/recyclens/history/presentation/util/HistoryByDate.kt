package com.recyclens.history.presentation.util

import com.recyclens.core.domain.history.HistoryWaste
import java.time.LocalDate

typealias HistoryByDate = Map<LocalDate, List<HistoryWaste>>