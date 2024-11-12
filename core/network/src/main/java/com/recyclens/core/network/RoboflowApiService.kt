package com.recyclens.core.network

import com.recyclens.core.network.dto.PredictionResultDto
import retrofit2.Response

interface RoboflowApiService {

    suspend fun getPrediction(): Response<PredictionResultDto>

    companion object {
        const val BASE_URL = "https://detect.roboflow.com/"
    }
}