package com.recyclens.core.network

import com.recyclens.core.network.dto.PredictionResultDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RoboflowApiService {

    @POST("/${MODEL_ID}/${MODEL_VERSION}")
    suspend fun getPrediction(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Body image: RequestBody
    ): Response<PredictionResultDto>

    companion object {
        const val BASE_URL = "https://detect.roboflow.com/"
        const val MODEL_ID = "recycling-classification-ajsxm"
        const val MODEL_VERSION = "2"
    }

}