@file:OptIn(ExperimentalCoroutinesApi::class)

package com.recyclens.scanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recyclens.core.domain.history.HistoryWaste
import com.recyclens.core.domain.history.HistoryRepository
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.core.domain.util.Result
import com.recyclens.scanner.domain.ClassificationRepository
import com.recyclens.scanner.presentation.util.compressImageToTargetSize
import com.recyclens.scanner.presentation.util.resize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val classificationRepository: ClassificationRepository,
    private val settingsRepository: SettingsRepository,
    private val historyRepository: HistoryRepository
): ViewModel() {

    private val _state = MutableStateFlow(ScannerState())
    val state = _state.asStateFlow()

    init {
        settingsRepository.language
            .onEach { language ->
                _state.update {
                    it.copy(currentLanguage = language)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ScannerAction) {
        when(action) {
            is ScannerAction.ScanImage -> {
                _state.update {
                    it.copy(isLoading = true)
                }
            }
            is ScannerAction.OnImageCapture -> {
                viewModelScope.launch(Dispatchers.IO.limitedParallelism(1)) {
                    val imageByteArray = withContext(Dispatchers.Default) {
                        action.image
                            .resize(640, 640)
                            .compressImageToTargetSize(512)
                    }
                    when(val result = classificationRepository.getPrediction(imageByteArray)) {
                        is Result.Success -> {
                            historyRepository.addHistoryWaste(
                                historyWaste = HistoryWaste(
                                    image = imageByteArray,
                                    wasteClass = result.data.wasteClass,
                                )
                            )
                            withContext(Dispatchers.Main) {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = false,
                                        classificationPrediction = result.data
                                    )
                                }
                            }
                        }
                        is Result.Error -> {
                            withContext(Dispatchers.Main) {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is ScannerAction.DismissPredictionDialog -> {
                _state.update {
                    it.copy(classificationPrediction = null)
                }
            }
            is ScannerAction.NavigateToInformation -> {
                _state.update {
                    it.copy(
                        classificationPrediction = null,
                        showUnfinishedFeatureDialog = false
                    )
                }
            }
            is ScannerAction.ShowWrongSuggestionsDialog -> {
                _state.update {
                    it.copy(showWrongSuggestionDialog = true)
                }
            }
            is ScannerAction.DismissWrongSuggestionsDialog -> {
                _state.update {
                    it.copy(showWrongSuggestionDialog = false)
                }
            }
            is ScannerAction.OnImageCaptureError -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
            is ScannerAction.DismissErrorDialog -> {
                _state.update {
                    it.copy(isError = false)
                }
            }
            is ScannerAction.ShowLanguageDialog -> {
                _state.update {
                    it.copy(showLanguageDialog = true)
                }
            }
            is ScannerAction.SetLanguage -> {
                viewModelScope.launch {
                    settingsRepository.setLanguage(action.language)
                }
            }
            is ScannerAction.HideLanguageDialog -> {
                _state.update {
                    it.copy(showLanguageDialog = false)
                }
            }
            is ScannerAction.ShowUnfinishedFeatureDialog -> {
                _state.update {
                    it.copy(showUnfinishedFeatureDialog = true)
                }
            }
            is ScannerAction.DismissUnfinishedFeatureDialog -> {
                _state.update {
                    it.copy(showUnfinishedFeatureDialog = false)
                }
            }
            is ScannerAction.ToggleFlash -> {
                _state.update { state ->
                    state.copy(isFlashOn = !state.isFlashOn)
                }
            }
            is ScannerAction.SubmitCameraPermissionInfo -> {
                _state.update { state ->
                    state.copy(
                        acceptedCameraPermission = action.acceptedCameraPermission,
                        showCameraPermissionRationale = action.showCameraPermissionRationale
                    )
                }
            }
            is ScannerAction.RequestCameraPermission -> {
                _state.update { state ->
                    state.copy(
                        requestedCameraPermission = true,
                        acceptedCameraPermission = action.acceptedCameraPermission,
                        showCameraPermissionRationale = action.showCameraPermissionRationale
                    )
                }
            }
            else -> Unit
        }
    }
}