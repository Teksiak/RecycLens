package com.recyclens.scanner.presentation

import com.recyclens.core.domain.settings.Language
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.scanner.domain.ClassificationPrediction

data class ScannerState(
    val classificationPrediction: ClassificationPrediction? = null,
    val isFlashOn: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val showLanguageDialog: Boolean = false,
    val currentLanguage: Language = SettingsRepository.DEFAULT_LANGUAGE,
    val requestedCameraPermission: Boolean = false,
    val acceptedCameraPermission: Boolean = false,
    val showCameraPermissionRationale: Boolean = false,
)
