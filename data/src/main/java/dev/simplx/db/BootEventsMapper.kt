package dev.simplx.db

import dev.simplx.domain.bootevent.BootEvent

object BootEventsMapper {
    fun map(bootEventModel: BootEventModel): BootEvent {
        return BootEvent(bootEventModel.bootTime)
    }

    fun map(bootEventModel: BootEvent): BootEventModel {
        return BootEventModel(0, bootEventModel.bootTime)
    }
}