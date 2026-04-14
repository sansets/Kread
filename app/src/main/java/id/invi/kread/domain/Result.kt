package id.invi.kread.domain

import androidx.compose.runtime.Composable

sealed interface Result<out D> {
    data object Default: Result<Nothing>
    data class Success<out D>(val data: D): Result<D>
    data class Error(val exception: Exception): Result<Nothing>
    data object Loading: Result<Nothing>

    companion object {
        fun <T> Result<T>.getSuccessData(): T? = (this as? Success)?.data
    }
}

inline fun <T, R> Result<T>.map(map: (T) -> R): Result<R> {
    return when(this) {
        is Result.Default -> Result.Default
        is Result.Error -> Result.Error(exception)
        is Result.Success -> Result.Success(map(data))
        is Result.Loading -> Result.Loading
    }
}

fun <T> Result<T>.asEmptyDataResult(): EmptyResult {
    return map {  }
}

suspend fun <T> Result<T>.onSuccessSuspend(action: suspend (T) -> Unit): Result<T> {
    return when(this) {
        is Result.Default -> this
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
        is Result.Loading -> this
    }
}

suspend fun <T> Result<T>.onErrorSuspend(action: suspend (Exception) -> Unit): Result<T> {
    return when(this) {
        is Result.Default -> this
        is Result.Error -> {
            action(exception)
            this
        }
        is Result.Success -> this
        is Result.Loading -> this
    }
}

suspend fun <T> Result<T>.onLoadingSuspend(action: suspend () -> Unit): Result<T> {
    return when(this) {
        is Result.Loading -> {
            action()
            this
        }
        else -> this
    }
}

inline fun <T> Result<T>.onSuccessSync(action: (T) -> Unit): Result<T> {
    return when(this) {
        is Result.Default -> this
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
        is Result.Loading -> this
    }
}

inline fun <T> Result<T>.onErrorSync(action: (Exception) -> Unit): Result<T> {
    return when(this) {
        is Result.Default -> this
        is Result.Error -> {
            action(exception)
            this
        }
        is Result.Success -> this
        is Result.Loading -> this
    }
}

typealias EmptyResult = Result<Unit>

@Suppress("ComposableNaming")
@Composable
fun <T> Result<T>.checkResult(
    onDefault: @Composable () -> Unit = {},
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (Exception) -> Unit = {},
    onSuccess: @Composable (T) -> Unit = {},
) {
    when (this) {
        is Result.Default -> onDefault()
        is Result.Loading -> onLoading()
        is Result.Error -> onError(this.exception)
        is Result.Success -> onSuccess(this.data)
    }
}

suspend fun <T> Result<T>.checkResultNonComposable(
    onDefault: suspend () -> Unit = {},
    onLoading: suspend () -> Unit = {},
    onError: suspend (Exception) -> Unit = {},
    onSuccess: suspend (T) -> Unit = {},
) {
    when (this) {
        is Result.Default -> onDefault()
        is Result.Loading -> onLoading()
        is Result.Error -> onError(this.exception)
        is Result.Success -> onSuccess(this.data)
    }
}