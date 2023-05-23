package dev.simplx.db

import dev.simplx.domain.bootevent.BootEvent
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BootEventDataSource @Inject constructor(
    private val bootEventsDao: BootEventsDao,

) {
    fun observeAll() = bootEventsDao.observeAll().map {
        it.map {
            BootEventsMapper.map(it)
        }
    }

    fun insertBootEvent(bootEvent: BootEvent) {
        bootEventsDao.insertBootEvent(BootEventsMapper.map(bootEvent))
    }
}