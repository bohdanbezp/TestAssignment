package dev.simplx.domain.bootevent

import dev.simplx.domain.common.Completion

class InsertBootEventUseCase(
    private val bootEventsRepository: BootEventsRepository
)  {
        suspend fun invoke(bootEvent: BootEvent): Completion<Throwable> {
            return bootEventsRepository.insertBootEvent(bootEvent)
        }
}