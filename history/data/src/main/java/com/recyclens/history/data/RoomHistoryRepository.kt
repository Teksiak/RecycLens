package com.recyclens.history.data

import com.recyclens.core.domain.history.ClassifiedWaste
import com.recyclens.core.domain.history.ClassifiedWastesByDate
import com.recyclens.core.domain.history.HistoryRepository
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.history.database.HistoryDao
import com.recyclens.history.database.toClassifiedWaste
import com.recyclens.history.database.toHistoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class RoomHistoryRepository(
    private val applicationScope: CoroutineScope,
    private val historyDao: HistoryDao,
    private val settingsRepository: SettingsRepository
): HistoryRepository {

    private val historySize = MutableStateFlow(SettingsRepository.DEFAULT_HISTORY_SIZE)

    init {
        settingsRepository.historySize
            .onEach { newSize ->
                historySize.value = newSize
                correctHistorySize(newSize)
            }
            .launchIn(applicationScope)
    }

    private suspend fun correctHistorySize(newSize: Int) {
        val oldSize = historyDao.getHistoryCount()
        if(newSize < oldSize) {
            val correction = oldSize - newSize
            historyDao.correctHistorySize(correction)
        }
    }

    override fun getWasteHistory(): Flow<List<ClassifiedWastesByDate>> {
        return historyDao.getHistory()
            .map { history ->
                history.groupBy { it.date.toLocalDate() }
                    .map { (date, classifiedWastes) ->
                        ClassifiedWastesByDate(
                            date = date,
                            classifiedWastes = classifiedWastes.map { it.toClassifiedWaste() }
                        )
                    }
            }
    }

    override suspend fun addClassifiedWaste(classifiedWaste: ClassifiedWaste) {
        historyDao.addToHistory(classifiedWaste.toHistoryEntity(), historySize.value)
    }

    override suspend fun removeClassifiedWaste(classifiedWaste: ClassifiedWaste) {
        historyDao.deleteHistoryById(classifiedWaste.id)
    }
}