package com.recyclens.scanner.presentation

interface ScannerAction {
    data class ScanImage(
        val image: ByteArray
    ): ScannerAction
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