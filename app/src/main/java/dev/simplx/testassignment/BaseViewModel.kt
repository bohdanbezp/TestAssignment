package dev.simplx.testassignment

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistry
import dev.simplx.testassignment.extensions.Tuple2
import dev.simplx.testassignment.extensions.Tuple3
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import kotlin.reflect.KProperty1

abstract class BaseViewModel<S : BaseViewState, M : Any>(initialState: S) :
    ViewModel(),
    SavedStateRegistry.SavedStateProvider {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> get() = _state

    private val _messages = Channel<M>(Channel.UNLIMITED)
    val messages get() = _messages.receiveAsFlow()

    protected abstract val tag: String

    protected val log: Timber.Tree
        get() = Timber.tag(tag)

    private val stateMutex = Mutex()

    protected suspend fun setState(reducer: S.() -> S) {
        stateMutex.withLock {
            _state.value = _state.value.reducer()
        }
    }

    protected fun updateState(reducer: S.() -> S) {
        viewModelScope.launch {
            setState(reducer)
        }
    }

    protected fun sendMessage(message: M) {
        _messages.trySend(message)
            .onFailure { log.e(it, "Channel: Failed to send a message") }
    }


    @CallSuper
    override fun saveState(): Bundle {
        return _state.value.saveState()
    }

    protected inline fun collectState(crossinline collector: suspend (S) -> Unit): Job {
        return viewModelScope.launch {
            state.collect {
                collector(it)
            }
        }
    }

    inline fun <A> collectState(
        prop1: KProperty1<S, A>,
        crossinline collector: suspend (A) -> Unit
    ) {
        viewModelScope.launch {
            state.map { prop1.get(it) }
                .distinctUntilChanged()
                .collect {
                    collector(it)
                }
        }
    }

    protected inline fun <A, B> collectState(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        crossinline collector: suspend (A, B) -> Unit
    ) {
        viewModelScope.launch {
            state.map { Tuple2(prop1.get(it), prop2.get(it)) }
                .distinctUntilChanged()
                .collect { (a, b) -> collector(a, b) }
        }
    }

    /**
     * Convenience method to collect multiple properties from a view state. The collector will only be invoked when
     * one of the properties has changed.
     */
    protected inline fun <A, B, C> collectState(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        prop3: KProperty1<S, C>,
        crossinline collector: suspend (A, B, C) -> Unit
    ) {
        viewModelScope.launch {
            state.map { Tuple3(prop1.get(it), prop2.get(it), prop3.get(it)) }
                .distinctUntilChanged()
                .collect { (a, b, c) -> collector(a, b, c) }
        }
    }

}

interface BaseViewState {
    /**
     * Used to persist the state of the view through process death.
     */
    fun saveState(): Bundle = Bundle.EMPTY
}