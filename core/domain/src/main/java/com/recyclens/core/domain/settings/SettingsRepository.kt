package com.recyclens.core.domain.settings

import com.recyclens.core.domain.util.DataError
import com.recyclens.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val language: Flow<Language>
    val historySize: Flow<Int>

    suspend fun setLanguage(language: Language): EmptyResult<DataError.Local>
    suspend fun setHistorySize(size: Int): EmptyResult<DataError.Local>

    companion object {
        const val DATASTORE_NAME = "settings"
        const val LANGUAGE_NAME = "language"
        const val HISTORY_SIZE = "history_size"

        const val DEFAULT_HISTORY_SIZE = 30
        val DEFAULT_LANGUAGE = Language.POLISH

        val HISTORY_SIZES = (10..50).toList()
    }
}