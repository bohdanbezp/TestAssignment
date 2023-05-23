package dev.simplx.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BootEventsDao {
    @Query("SELECT * FROM BootEventModel ORDER BY bootTime")
    fun observeAll(): Flow<List<BootEventModel>>

    @Insert
    fun insertBootEvent(bootEvent: BootEventModel)
}