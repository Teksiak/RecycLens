package com.recyclens.settings.presentation

sealed interface SettingsAction {
    data object ShowHistorySizeDialog: SettingsAction
    data class SetHistorySize(val size: Int): SettingsAction
    data object HideHistorySizeDialog: SettingsAction
    data object NavigateBack: SettingsAction
}