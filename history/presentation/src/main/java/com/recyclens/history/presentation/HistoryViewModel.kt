package com.recyclens.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recyclens.core.domain.history.HistoryRepository
import com.recyclens.core.domain.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val settingsRepository: SettingsRepository
): ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    init {
        settingsRepository.historySize
            .onEach { historySize ->
                _state.update {
                    it.copy(
                        settingsHistorySize = historySize
                    )
                }
            }
            .launchIn(viewModelScope)

        settingsRepository.language
            .onEach { language ->
                _state.update {
                    it.copy(
                        currentLanguage = language
                    )
                }
            }
            .launchIn(viewModelScope)

        historyRepository.getWasteHistory()
            .onEach { history ->
                val historyByDate = history
                    .groupBy { it.date.toLocalDate() }
                _state.update {
                    it.copy(
                        history = historyByDate,
                        expandedDates = if(!it.loadedHistory && historyByDate.isNotEmpty()) {
                            val firstHistoryItem = historyByDate.keys.first()
                            if((historyByDate[firstHistoryItem]?.size ?: 0) <= 6) {
                                listOf(historyByDate.keys.first())
                            } else {
                                emptyList()
                            }
                        } else it.expandedDates,
                        loadedHistory = true
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: HistoryAction) {
        when(action) {
            is HistoryAction.ToggleExpandDate -> {
                _state.update { state ->
                    state.copy(
                        expandedDates = if(state.expandedDates.contains(action.date)) {
                            state.expandedDates - action.date
                        } else {
                            state.expandedDates + action.date
                        }
                    )
                }
            }
            is HistoryAction.RemoveHistoryWaste -> {
                _state.update { state ->
                    state.copy(
                        historyWasteToRemove = action.historyWaste
                    )
                }
            }
            is HistoryAction.ConfirmRemove -> {
                viewModelScope.launch {
                    _state.value.historyWasteToRemove?.let { wasteToRemove ->
                        historyRepository.removeHistoryWaste(wasteToRemove)
                        _state.update {
                            it.copy(
                                historyWasteToRemove = null
                            )
                        }
                    }
                }
            }
            is HistoryAction.CancelRemove -> {
                _state.update { state ->
                    state.copy(
                        historyWasteToRemove = null
                    )
                }
            }
            else -> Unit
        }
    }

}