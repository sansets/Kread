package id.invi.kread.data.remote.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddTrackingRequest(
    @SerialName("book_title")
    val bookTitle: String? = null,
    @SerialName("reading_date")
    val readingDate: String? = null,
    @SerialName("reading_start_time")
    val readingStartTime: String? = null,
    @SerialName("reading_end_time")
    val readingEndtime: String? = null,
)
