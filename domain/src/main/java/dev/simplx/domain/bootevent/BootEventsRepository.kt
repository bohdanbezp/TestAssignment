package dev.simplx.domain.bootevent

import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface BootEventsRepository {
    fun observeAll(): Flow<List<BootEvent>>
    fun insertBootEvent(bootEvent: BootEvent): Result<Unit, Throwable>
}