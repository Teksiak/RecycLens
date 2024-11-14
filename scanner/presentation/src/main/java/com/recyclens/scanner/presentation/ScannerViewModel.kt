package com.recyclens.scanner.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recyclens.core.domain.util.Result
import com.recyclens.scanner.domain.ClassificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
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
                viewModelScope.launch(Dispatchers.IO) {
                    val imageByteArray = withContext(Dispatchers.Default) {
                        val stream = ByteArrayOutputStream()
                        action.image.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                        stream.toByteArray()
                    }
                    when(val result = classificationRepository.getPrediction(imageByteArray)) {
                        is Result.Success -> {
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
                    it.copy(classificationPrediction = null)
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