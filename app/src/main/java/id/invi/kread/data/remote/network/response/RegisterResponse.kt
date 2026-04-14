package id.invi.kread.data.remote.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerialName("app_metadata")
    val appMetadata: AppMetadata? = null,
    @SerialName("aud")
    val aud: String? = null,
    @SerialName("confirmation_sent_at")
    val confirmationSentAt: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("identities")
    val identities: List<Identity?>? = null,
    @SerialName("is_anonymous")
    val isAnonymous: Boolean? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("role")
    val role: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("user_metadata")
    val userMetadata: UserMetadata? = null
) {
    @Serializable
    data class AppMetadata(
        @SerialName("provider")
        val provider: String? = null,
        @SerialName("providers")
        val providers: List<String?>? = null
    )

    @Serializable
    data class Identity(
        @SerialName("created_at")
        val createdAt: String? = null,
        @SerialName("email")
        val email: String? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("identity_data")
        val identityData: IdentityData? = null,
        @SerialName("identity_id")
        val identityId: String? = null,
        @SerialName("last_sign_in_at")
        val lastSignInAt: String? = null,
        @SerialName("provider")
        val provider: String? = null,
        @SerialName("updated_at")
        val updatedAt: String? = null,
        @SerialName("user_id")
        val userId: String? = null
    ) {
        @Serializable
        data class IdentityData(
            @SerialName("email")
            val email: String? = null,
            @SerialName("email_verified")
            val emailVerified: Boolean? = null,
            @SerialName("phone_verified")
            val phoneVerified: Boolean? = null,
            @SerialName("sub")
            val sub: String? = null
        )
    }

    @Serializable
    data class UserMetadata(
        @SerialName("email")
        val email: String? = null,
        @SerialName("email_verified")
        val emailVerified: Boolean? = null,
        @SerialName("phone_verified")
        val phoneVerified: Boolean? = null,
        @SerialName("sub")
        val sub: String? = null
    )
}