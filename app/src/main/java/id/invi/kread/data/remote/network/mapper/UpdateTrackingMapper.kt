package id.invi.kread.data.remote.network.mapper

import id.invi.kread.data.remote.network.request.UpdateTrackingRequest
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.util.formatToServer

fun HabitTracking.toUpdateRequest(): UpdateTrackingRequest {
    return UpdateTrackingRequest(
        bookTitle = bookTitle,
        readingDate = readingDate.formatToServer(),
        readingStartTime = "$readingStartTime:00",
        readingEndtime = "$readingEndTime:00",
    )
}