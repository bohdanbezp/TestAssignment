package dev.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.simplx.data.BootEventRepositoryImpl
import dev.simplx.db.BootEventDataSource
import dev.simplx.domain.bootevent.BootEventsRepository
import dev.simplx.domain.bootevent.ObserveBootEventsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideObserveBootEventsUseCase(bootEventsRepository: BootEventsRepository): ObserveBootEventsUseCase {
        return ObserveBootEventsUseCase(bootEventsRepository)
    }
}
