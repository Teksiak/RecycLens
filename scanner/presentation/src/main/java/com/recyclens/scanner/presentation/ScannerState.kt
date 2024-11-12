package com.recyclens.scanner.presentation

data class ScannerState(
    val isFlashOn: Boolean = false,
    val isLoading: Boolean = false,
    val requestedCameraPermission: Boolean = false,
    val acceptedCameraPermission: Boolean = false,
    val showCameraPermissionRationale: Boolean = false,
)
