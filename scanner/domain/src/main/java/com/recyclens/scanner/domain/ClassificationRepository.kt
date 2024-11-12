package com.recyclens.scanner.domain

import com.recyclens.core.domain.util.DataError
import com.recyclens.core.domain.util.Result

interface ClassificationRepository {
    suspend fun getPrediction(): Result<ClassificationPrediction, DataError.Remote>
}