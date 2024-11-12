package com.recyclens.scanner.data.mapper

import com.recyclens.core.domain.WasteClass
import com.recyclens.core.network.dto.PredictionResultDto
import com.recyclens.scanner.domain.ClassificationPrediction

fun PredictionResultDto.toClassificationPrediction(): ClassificationPrediction {
    val bestPrediction = predictions.maxBy { it.confidence }
    return ClassificationPrediction(
        wasteClass = WasteClass.fromId(bestPrediction.classId),
        confidence = bestPrediction.confidence
    )
}