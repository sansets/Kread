package id.invi.kread.data.local.mapper

import id.invi.kread.data.local.room.entity.HabitTrackingEntity
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.util.formatToServer
import id.invi.kread.util.serverToDate
import java.util.Date

fun HabitTrackingEntity.toDomain(): HabitTracking {
    return HabitTracking(
        id = id,
        bookTitle = bookTitle,
        readingDate = readingDate.serverToDate() ?: Date(),
        readingStartTime = readingStartTime,
        readingEndTime = readingEndTime,
        isSynchronized = isSynchronized,
        isSynchronizing = isSynchronizing,
    )
}

fun List<HabitTrackingEntity>.toDomain(): List<HabitTracking> {
    return map { it.toDomain() }
}

fun HabitTracking.toEntity(
    isSynchronized: Boolean = false,
    isSynchronizing: Boolean = false,
    isDeleted: Boolean = false
): HabitTrackingEntity {
    return HabitTrackingEntity(
        id = id,
        bookTitle = bookTitle,
        readingDate = readingDate.formatToServer(),
        readingStartTime = readingStartTime,
        readingEndTime = readingEndTime,
        isSynchronized = isSynchronized,
        isSynchronizing = isSynchronizing,
        isDeleted = isDeleted,
    )
}
