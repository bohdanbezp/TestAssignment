package dev.simplx.testassignment.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simplx.domain.bootevent.BootEvent
import dev.simplx.domain.bootevent.ObserveBootEventsUseCase
import dev.simplx.testassignment.BaseViewModel
import dev.simplx.testassignment.BaseViewState
import dev.simplx.testassignment.extensions.Async
import dev.simplx.testassignment.extensions.Complete
import dev.simplx.testassignment.extensions.Uninitialized
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class DashboardViewState(
    val bootEvents: Async<List<BootEvent>, Throwable> = Uninitialized,
) : BaseViewState

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val observeBootEventsUseCase: ObserveBootEventsUseCase,
) : BaseViewModel<DashboardViewState, Unit>(DashboardViewState()) {
    override val tag: String
        get() = "DashboardViewModel"

    init {
        observeBootEvents()
    }

    private fun observeBootEvents() {
        observeBootEventsUseCase.observe().catch { error ->
            setState { copy(bootEvents = Complete.withError(error)) }
        }.onEach { bootEvents ->
            updateState {
                copy(
                    bootEvents = Complete.withSuccess(bootEvents)
                )
            }
        }.launchIn(viewModelScope)
    }
}