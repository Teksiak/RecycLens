package com.recyclens.scanner.domain

import com.recyclens.core.domain.WasteClass

data class ClassificationPrediction(
    val wasteClass: WasteClass,
    val confidence: Double
)
