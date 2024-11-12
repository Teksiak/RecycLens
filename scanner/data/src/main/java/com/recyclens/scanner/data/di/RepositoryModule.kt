package com.recyclens.scanner.data.di

import com.recyclens.scanner.data.RoboflowClassificationRepository
import com.recyclens.scanner.domain.ClassificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRoboflowClassificationRepository(
        roboflowClassificationRepository: RoboflowClassificationRepository
    ): ClassificationRepository
}