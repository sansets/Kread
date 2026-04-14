package id.invi.kread.data.remote.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackingResponse(
    @SerialName("book_title")
    val bookTitle: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("reading_date")
    val readingDate: String? = null,
    @SerialName("reading_end_time")
    val readingEndTime: String? = null,
    @SerialName("reading_start_time")
    val readingStartTime: String? = null,
    @SerialName("user_id")
    val userId: String? = null
)