package com.recyclens.scanner.presentation

import android.graphics.Bitmap

interface ScannerAction {
    data object ScanImage: ScannerAction
    data class OnImageCapture(
        val image: Bitmap
    ): ScannerAction
    data class OnImageCaptureError(
        val error: String
    ): ScannerAction
    data object DismissPredictionDialog: ScannerAction
    data object NavigateToHistory: ScannerAction
    data object NavigateToSettings: ScannerAction
    data object NavigateToAboutUs: ScannerAction
    data object ToggleFlash: ScannerAction
    data class SubmitCameraPermissionInfo(
        val acceptedCameraPermission: Boolean,
        val showCameraPermissionRationale: Boolean
    ): ScannerAction

    class RequestCameraPermission(
        val acceptedCameraPermission: Boolean,
        val showCameraPermissionRationale: Boolean
    ) : ScannerAction
}