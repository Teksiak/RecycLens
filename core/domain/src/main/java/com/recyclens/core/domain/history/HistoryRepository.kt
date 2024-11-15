package com.recyclens.core.domain.history

import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getWasteHistory(): Flow<List<HistoryWaste>>

    suspend fun addHistoryWaste(historyWaste: HistoryWaste)

    suspend fun removeHistoryWaste(historyWaste: HistoryWaste)
}