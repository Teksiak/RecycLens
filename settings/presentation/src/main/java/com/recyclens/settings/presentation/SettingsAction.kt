package com.recyclens.settings.presentation

import com.recyclens.core.domain.settings.Language

sealed interface SettingsAction {
    data object ShowLanguageDialog: SettingsAction
    data class SetLanguage(val language: Language): SettingsAction
    data object HideLanguageDialog: SettingsAction
    data object ShowHistorySizeDialog: SettingsAction
    data class SetHistorySize(val size: Int): SettingsAction
    data object HideHistorySizeDialog: SettingsAction
    data object NavigateBack: SettingsAction
}