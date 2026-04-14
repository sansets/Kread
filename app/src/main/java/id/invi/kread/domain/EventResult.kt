package id.invi.kread.domain

import kotlinx.coroutines.channels.Channel

sealed class EventResult<out T> {
    class Loading<T> : EventResult<T>()
    data class Success<T>(val data: T) : EventResult<T>()
    data class Error(val exception: Exception): EventResult<Nothing>()

    companion object {
        fun <T> loading(): EventResult<T> = Loading()
        fun <T> success(data: T): EventResult<T> = Success(data)
        fun <T> error(message: String?): EventResult<T> = Error(Exception(message))
        fun <T> error(exception: Exception): EventResult<T> = Error(exception)
    }
}

suspend fun <T> Channel<EventResult<T>>.sendLoading() {
    try {
        send(EventResult.loading())
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

suspend fun <T> Channel<EventResult<T>>.sendSuccess(data: T) {
    try {
        send(EventResult.success(data))
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

suspend fun <T> Channel<EventResult<T>>.sendError(message: String?) {
    try {
        send(EventResult.error(message))
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

suspend fun <T> Channel<EventResult<T>>.sendError(exception: Exception) {
    try {
        send(EventResult.error(exception))
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

suspend fun <T>Channel<EventResult<T>>.checkChannelValue(
    onLoading: () -> Unit = { },
    onSuccess: (T) -> Unit = { },
    onError: (Exception?) -> Unit = { _ -> }
) {
    try {
        for (event in this) {
            when (event) {
                is EventResult.Loading<T> -> { onLoading() }
                is EventResult.Success<T> -> { onSuccess(event.data) }
                is EventResult.Error -> { onError(event.exception) }
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}