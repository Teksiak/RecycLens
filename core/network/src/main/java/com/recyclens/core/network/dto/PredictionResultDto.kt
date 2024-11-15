package com.recyclens.core.network.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PredictionResultDto(
    val predictions: List<ClassificationPrediction>,
    val top: String,
    val confidence: Double
)

@Keep
@Serializable
data class ClassificationPrediction(
    @SerializedName("class")
    val wasteClass: String,
    val classId: Int,
    val confidence: Double
)