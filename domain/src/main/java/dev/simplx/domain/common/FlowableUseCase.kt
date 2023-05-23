package dev.simplx.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowableParamUseCase<V, P>(
    private val dispatcher: CoroutineDispatcher
) {

    fun observe(params: P): Flow<V> {
        return execute(params).flowOn(dispatcher)
            .catch { error ->
                throw error
            }
    }

    protected abstract fun execute(params: P): Flow<V>
}

abstract class FlowableUseCase<V>(
    dispatcher: CoroutineDispatcher
) : FlowableParamUseCase<V, Unit>(dispatcher) {

    fun observe(): Flow<V> = observe(Unit)

    final override fun execute(params: Unit): Flow<V> = execute()

    protected abstract fun execute(): Flow<V>
}