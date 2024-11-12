package com.recyclens.scanner.presentation

import androidx.lifecycle.ViewModel
import com.recyclens.scanner.domain.ClassificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val classificationRepository: ClassificationRepository
): ViewModel() {

    private val _state = MutableStateFlow(ScannerState())
    val state = _state.asStateFlow()

    fun onAction(action: ScannerAction) {
        when(action) {
            is ScannerAction.ScanImage -> {
                _state.update {
                    it.copy(isLoading = true)
                }
            }
            is ScannerAction.OnImageCapture -> {
                _state.update {
                    it.copy(isLoading = false)
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