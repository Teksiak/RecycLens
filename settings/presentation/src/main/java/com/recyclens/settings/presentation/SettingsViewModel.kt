package com.recyclens.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recyclens.core.domain.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        updateHistorySizeSetting()

        updateLanguageSetting()
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ShowLanguageDialog -> {
                _state.update {
                    it.copy(showLanguageDialog = true)
                }
            }
            is SettingsAction.SetLanguage -> {
                viewModelScope.launch {
                    settingsRepository.setLanguage(action.language)
                }
            }
            is SettingsAction.HideLanguageDialog -> {
                _state.update {
                    it.copy(showLanguageDialog = false)
                }
            }
            is SettingsAction.ShowHistorySizeDialog -> {
                _state.update {
                    it.copy(showHistorySizeDialog = true)
                }
            }
            is SettingsAction.SetHistorySize -> {
                viewModelScope.launch {
                    settingsRepository.setHistorySize(action.size)
                }
            }
            is SettingsAction.HideHistorySizeDialog -> {
                _state.update {
                    it.copy(showHistorySizeDialog = false)
                }
            }
            else -> Unit
        }
    }

    private fun updateHistorySizeSetting() {
        settingsRepository.historySize
            .onEach { historySize ->
                _state.update {
                    it.copy(
                        historySize = historySize,
                        areSettingsLoaded = true
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun updateLanguageSetting() {
        settingsRepository.language
            .onEach { language ->
                _state.update {
                    it.copy(
                        language = language,
                        areSettingsLoaded = true
                    )
                }
            }.launchIn(viewModelScope)
    }

}