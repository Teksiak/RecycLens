package com.recyclens.core.network

import com.recyclens.core.network.dto.PredictionResultDto
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import kotlin.io.encoding.Base64

@AndroidEntryPoint
interface RoboflowApiService {
    @FormUrlEncoded
    @POST("/recycling-classification-ajsxm/2?api_key=${BuildConfig.API_KEY}")
    suspend fun getPrediction(@Body base64: String): Response<PredictionResultDto>

}