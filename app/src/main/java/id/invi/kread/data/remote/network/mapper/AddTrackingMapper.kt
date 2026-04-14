package id.invi.kread.data.remote.network.mapper

import id.invi.kread.data.remote.network.request.AddTrackingRequest
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.util.formatToServer

fun HabitTracking.toRequest(): AddTrackingRequest {
    return AddTrackingRequest(
        id = id,
        bookTitle = bookTitle,
        readingDate = readingDate.formatToServer(),
        readingStartTime = "$readingStartTime:00",
        readingEndtime = "$readingEndTime:00",
    )
}