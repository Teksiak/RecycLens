package com.recyclens.scanner.presentation

import android.graphics.Bitmap
import com.recyclens.core.domain.settings.Language
import com.recyclens.core.presentation.Question

interface ScannerAction {
    data object ScanImage: ScannerAction

    data class OnImageCapture(
        val image: Bitmap
    ): ScannerAction

    data object DismissPredictionDialog: ScannerAction

    data class NavigateToInformation(
        val question: Question?
    ): ScannerAction

    data class OnImageCaptureError(
        val error: String
    ): ScannerAction
    data object DismissErrorDialog: ScannerAction

    data object ShowWrongSuggestionsDialog: ScannerAction

    data object DismissWrongSuggestionsDialog: ScannerAction

    data object ShowLanguageDialog: ScannerAction

    data class SetLanguage(
        val language: Language
    ): ScannerAction

    data object HideLanguageDialog: ScannerAction

    data object ShowUnfinishedFeatureDialog: ScannerAction

    data object DismissUnfinishedFeatureDialog: ScannerAction

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