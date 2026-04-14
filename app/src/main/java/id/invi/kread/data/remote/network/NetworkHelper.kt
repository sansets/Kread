package id.invi.kread.data.remote.network

import id.invi.kread.data.remote.RemoteResult
import id.invi.kread.data.remote.network.response.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.Response
import timber.log.Timber

@PublishedApi
internal val json = Json { ignoreUnknownKeys = true }

inline fun <reified T> safeCall(
    execute: () -> Response<T>
): RemoteResult<T> {
    val response = try {
        execute()
    } catch (ex: Exception) {
        Timber.e(ex)
        return RemoteResult.Error(ex)
    }

    return responseToResult(response)
}

inline fun <reified T> responseToResult(
    response: Response<T>
): RemoteResult<T> {
    return when (response.code()) {
        in 200..299 -> {
            val body = response.body()
            @Suppress("UNCHECKED_CAST")
            RemoteResult.Success(
                if (body == null && T::class == Unit::class) Unit as T
                else body as T
            )
        }

        else -> {
            val errorBody = response.errorBody()?.string()
            val errorMessage = try {
                if (!errorBody.isNullOrBlank()) {
                    val errorResponse = json.decodeFromString<ErrorResponse>(errorBody)

                    if (!errorResponse.message.isNullOrEmpty()) errorResponse.message
                    else if (!errorResponse.msg.isNullOrEmpty()) errorResponse.msg
                    else response.message()
                } else {
                    response.message()
                }
            } catch (_: Exception) {
                response.message()
            }
            RemoteResult.Error(Exception(errorMessage))
        }
    }
}