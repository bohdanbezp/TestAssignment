package dev.simplx.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class CompletionParamUseCase<E : Throwable, P>(
    private val dispatcher: CoroutineDispatcher,
) {

    suspend fun invoke(params: P): Completion<E> = withContext(dispatcher) {
        execute(params)
    }

    protected abstract suspend fun execute(params: P): Completion<E>
}

abstract class CompletionUseCase<E : Throwable>(
    dispatcher: CoroutineDispatcher,
) : CompletionParamUseCase<E, Unit>(dispatcher) {

    suspend fun invoke(): Completion<E> = invoke(Unit)

    final override suspend fun execute(params: Unit): Completion<E> = execute()

    protected abstract suspend fun execute(): Completion<E>
}
