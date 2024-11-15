package com.recyclens.settings.data.di

import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.settings.data.DataStoreSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: DataStoreSettingsRepository
    ): SettingsRepository
}