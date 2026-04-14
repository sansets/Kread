package id.invi.kread.data.remote.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("message")
    val message: String? = null,
    @SerialName("msg")
    val msg: String? = null,
    @SerialName("hint")
    val hint: String? = null
)
