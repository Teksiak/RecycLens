package com.recyclens.scanner.data

import android.util.Base64
import android.util.Log
import androidx.camera.core.ImageProxy
import com.recyclens.core.domain.util.DataError
import com.recyclens.scanner.domain.ClassificationPrediction
import com.recyclens.scanner.domain.ClassificationRepository
import com.recyclens.core.domain.util.Result
import com.recyclens.core.network.RoboflowApiService
import com.recyclens.core.network.util.safeApiCall
import com.recyclens.scanner.data.mapper.toClassificationPrediction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoboflowClassificationRepository @Inject constructor(
    private val apiService: RoboflowApiService,
): ClassificationRepository {

    override suspend fun getPrediction(image: ByteArray): Result<ClassificationPrediction, DataError.Remote> {
        return when (val result = safeApiCall {
            withContext(Dispatchers.IO) {
                val base64 = async(Dispatchers.Default) {
                    Base64.encodeToString(image, Base64.DEFAULT)
                }
                apiService.getPrediction(
                    base64 = base64.await()
                )
            }
        }) {
            is Result.Error -> result
            is Result.Success -> {
                Log.d("RoboflowClassificationRepository", "getPrediction: ${result.data}")
                val predictionDto = result.data
                if(predictionDto.predictions.isEmpty()) {
                    return Result.Error(DataError.Remote.UNKNOWN_ERROR)
                }
                Result.Success(predictionDto.toClassificationPrediction())
            }
        }
    }
}