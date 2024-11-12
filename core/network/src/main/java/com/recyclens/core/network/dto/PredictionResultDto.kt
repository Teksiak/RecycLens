package com.recyclens.core.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PredictionResultDto(
    val predictions: List<ClassificationPrediction>,
    val top: String,
    val confidence: Double
)

@Serializable
data class ClassificationPrediction(
    @SerializedName("class")
    val wasteClass: String,
    val classId: Int,
    val confidence: Double
)