package id.invi.kread.data.remote.network.mapper

import id.invi.kread.data.remote.network.response.TrackingResponse
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.util.formatTime
import id.invi.kread.util.serverToDate
import java.util.Date

fun List<TrackingResponse>.toDomain(): List<HabitTracking> {
    return this.map { it.toDomain() }
}

fun TrackingResponse.toDomain(): HabitTracking {
    return HabitTracking(
        id = id?.toString().orEmpty(),
        bookTitle = bookTitle.orEmpty(),
        readingDate = readingDate?.serverToDate() ?: Date(),
        readingStartTime = readingStartTime.orEmpty().formatTime(),
        readingEndTime = readingEndTime.orEmpty().formatTime(),
        isSynchronized = true,
    )
}
