package dev.simplx.testassignment.extensions

sealed class Async<out V, out E>

object Uninitialized : Async<Nothing, Nothing>(), Incomplete
object Loading : Async<Nothing, Nothing>(), Incomplete

data class Success<out V>(val value: V) : Complete<V, Nothing>()

data class Fail<out E>(val error: E) : Complete<Nothing, E>()

sealed class Complete<out V, out E> : Async<V, E>() {
    companion object {
        fun <E> withError(error: E): Fail<E> {
            return Fail(error)
        }

        fun <V> withSuccess(value: V): Success<V> {
            return Success(value)
        }
    }
}

val <V, E> Async<V, E>.value: V?
    get() = if (this is Success) value else null

interface Incomplete
