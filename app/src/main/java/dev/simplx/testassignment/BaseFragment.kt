package dev.simplx.testassignment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStateAtLeast
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {
    fun <S : BaseViewState> BaseViewModel<S, *>.collectState(
        owner: LifecycleOwner = this@BaseFragment.viewLifecycleOwner,
        minState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: suspend (S) -> Unit
    ) = state.collectLifecycle(owner, minState, collector)

    fun <M : Any> BaseViewModel<*, M>.collectMessages(
        owner: LifecycleOwner = this@BaseFragment.viewLifecycleOwner,
        minState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: suspend (M) -> Unit
    ) = messages.collectLifecycle(owner, minState, collector)

    private inline fun <T> Flow<T>.collectLifecycle(
        owner: LifecycleOwner,
        minState: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline collector: suspend (T) -> Unit
    ): Job {
        return owner.lifecycleScope.launch {
            owner.lifecycle.whenStateAtLeast(minState) {
                collect { value -> collector(value) }
            }
        }
    }
}