package com.recyclens.history.database.di

import android.content.Context
import androidx.room.Room
import com.recyclens.history.database.RecyclensDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideRecyclensDatabase(
        @ApplicationContext context: Context
    ): RecyclensDatabase {
        return Room.databaseBuilder(
            context,
            RecyclensDatabase::class.java,
            "recyclens.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(
        recyclensDatabase: RecyclensDatabase
    ) = recyclensDatabase.historyDao
}