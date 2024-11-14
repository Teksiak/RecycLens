package com.recyclens.core.domain.history

import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getWasteHistory(): Flow<List<HistoryWaste>>

    suspend fun addClassifiedWaste(historyWaste: HistoryWaste)

    suspend fun removeClassifiedWaste(historyWaste: HistoryWaste)
}