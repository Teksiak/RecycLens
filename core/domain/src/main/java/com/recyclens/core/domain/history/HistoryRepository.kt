package com.recyclens.core.domain.history

import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getWasteHistory(): Flow<List<ClassifiedWastesByDate>>

    suspend fun addClassifiedWaste(classifiedWaste: ClassifiedWaste)

    suspend fun removeClassifiedWaste(classifiedWaste: ClassifiedWaste)
}