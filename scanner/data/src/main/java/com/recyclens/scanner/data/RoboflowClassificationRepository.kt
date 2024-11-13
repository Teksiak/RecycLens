package com.recyclens.scanner.data

import android.util.Base64
import com.recyclens.core.domain.util.DataError
import com.recyclens.core.domain.util.Result
import com.recyclens.core.network.RoboflowApiService
import com.recyclens.core.network.util.safeApiCall
import com.recyclens.scanner.data.mapper.toClassificationPrediction
import com.recyclens.scanner.domain.ClassificationPrediction
import com.recyclens.scanner.domain.ClassificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RoboflowClassificationRepository @Inject constructor(
    private val apiService: RoboflowApiService,
): ClassificationRepository {

    override suspend fun getPrediction(image: ByteArray): Result<ClassificationPrediction, DataError.Remote> {
        return when (val result = safeApiCall {
            withContext(Dispatchers.IO) {
                val imageBase64 = withContext(Dispatchers.Default) {
                    Base64.encodeToString(image, Base64.DEFAULT)
                }
                val imageRequestBody = imageBase64.toRequestBody("application/x-www-form-urlencoded".toMediaType())
                apiService.getPrediction(
                    image = imageRequestBody
                )
            }
        }) {
            is Result.Error -> result
            is Result.Success -> {
                val predictionDto = result.data
                if(predictionDto.predictions.isEmpty()) {
                    return Result.Error(DataError.Remote.UNKNOWN_ERROR)
                }
                Result.Success(predictionDto.toClassificationPrediction())
            }
        }
    }
}