package dev.simplx.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BootEventModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bootEventsDao(): BootEventsDao
}