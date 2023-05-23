package dev.simplx.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.simplx.data.BootEventRepositoryImpl
import dev.simplx.db.AppDatabase
import dev.simplx.db.BootEventDataSource
import dev.simplx.db.BootEventsDao
import dev.simplx.domain.bootevent.BootEventsRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideBootEventsRepository(bootEventRepositoryDataSource: BootEventDataSource): BootEventsRepository {
        return BootEventRepositoryImpl(bootEventRepositoryDataSource)
    }

    @Provides
    fun provideBootEventsDao(appDatabase: AppDatabase): BootEventsDao {
        return appDatabase.bootEventsDao()
    }

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "boots.db"
        )
            .build()
    }
}