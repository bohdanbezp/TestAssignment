package dev.simplx.domain.common

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


abstract class ResultUseCase<V, E : Throwable>(
    dispatcher: CoroutineDispatcher
) : ResultParamUseCase<V, E, Unit>(dispatcher) {

    suspend fun invoke(): Result<V, E> = invoke(Unit)

    final override suspend fun execute(params: Unit): Result<V, E> = execute()

    protected abstract suspend fun execute(): Result<V, E>
}

abstract class ResultParamUseCase<V, E : Throwable, P>(
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun invoke(params: P): Result<V, E> = withContext(dispatcher) {
        execute(params).onFailure { }
    }

    protected abstract suspend fun execute(params: P): Result<V, E>
}