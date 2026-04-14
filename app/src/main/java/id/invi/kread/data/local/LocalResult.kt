package id.invi.kread.data.local

sealed class LocalResult<out R> {
    data class Success<out T>(val data: T) : LocalResult<T>()
    data class Error(val exception: Exception) : LocalResult<Nothing>()
}