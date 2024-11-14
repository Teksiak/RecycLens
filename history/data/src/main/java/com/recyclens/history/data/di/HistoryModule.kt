package com.recyclens.history.data.di

import com.recyclens.core.domain.history.HistoryRepository
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.history.data.RoomHistoryRepository
import com.recyclens.history.database.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(
        applicationScope: CoroutineScope,
        historyDao: HistoryDao,
        settingsRepository: SettingsRepository,
    ): HistoryRepository = RoomHistoryRepository(applicationScope, historyDao, settingsRepository)
}