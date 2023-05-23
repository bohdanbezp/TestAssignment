package dev.simplx.domain.bootevent

import dev.simplx.domain.common.FlowableUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ObserveBootEventsUseCase(
    private val repository: BootEventsRepository,
) : FlowableUseCase<List<BootEvent>>(Dispatchers.IO) {

    override fun execute(): Flow<List<BootEvent>> {
        return repository.observeAll()
    }
}