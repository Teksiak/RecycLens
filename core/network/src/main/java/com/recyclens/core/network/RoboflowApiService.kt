package com.recyclens.core.network

import com.recyclens.core.network.dto.PredictionResultDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RoboflowApiService {
    @FormUrlEncoded
    @POST("/${MODEL_ID}/${MODEL_VERSION}")
    suspend fun getPrediction(
        @Field("api_key") apiKey: String = BuildConfig.API_KEY,
        @Body base64: String
    ): Response<PredictionResultDto>

    companion object {
        const val BASE_URL = "https://api.roboflow.com/"
        const val MODEL_ID = "recycling-classification-ajsxm"
        const val MODEL_VERSION = "2"
    }

}