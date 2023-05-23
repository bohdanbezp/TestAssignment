package dev.simplx.data

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.simplx.db.BootEventDataSource
import dev.simplx.domain.bootevent.BootEvent
import dev.simplx.domain.bootevent.BootEventsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BootEventRepositoryImpl @Inject constructor(
    val bootEventDataSource: BootEventDataSource
) : BootEventsRepository {
    override fun observeAll(): Flow<List<BootEvent>> {
        return bootEventDataSource.observeAll()
    }

    override fun insertBootEvent(bootEvent: BootEvent): Result<Unit, Throwable> {
        return Ok(bootEventDataSource.insertBootEvent(bootEvent))
    }
}