package com.recyclens.history.presentation

import com.recyclens.core.domain.history.HistoryWaste
import java.time.LocalDate

sealed interface HistoryAction {
    data class ToggleExpandDate(val date: LocalDate): HistoryAction
    data class RemoveHistoryWaste(val historyWaste: HistoryWaste): HistoryAction
    data object ConfirmRemove: HistoryAction
    data object CancelRemove: HistoryAction
    data object NavigateToSettings: HistoryAction
    data object NavigateBack: HistoryAction
}