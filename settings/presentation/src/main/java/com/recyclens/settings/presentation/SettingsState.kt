package com.recyclens.settings.presentation

import com.recyclens.core.domain.settings.Language
import com.recyclens.core.domain.settings.SettingsRepository

data class SettingsState(
    val language: Language = SettingsRepository.DEFAULT_LANGUAGE,
    val showLanguageDialog: Boolean = false,
    val historySize: Int = SettingsRepository.DEFAULT_HISTORY_SIZE,
    val showHistorySizeDialog: Boolean = false,
    val areSettingsLoaded: Boolean = false
)
