package id.invi.kread.data.remote.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("email")
    val email: String? = null,
    @SerialName("password")
    val password: String? = null,
)
