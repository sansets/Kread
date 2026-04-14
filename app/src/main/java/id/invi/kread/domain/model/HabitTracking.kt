package id.invi.kread.domain.model

import java.util.Calendar
import java.util.Date

data class HabitTracking(
    val id: String,
    val bookTitle: String,
    val readingDate: Date,
    val readingStartTime: String,
    val readingEndTime: String,
    val isSynchronized: Boolean,
    val isSynchronizing: Boolean = false,
)

fun getDummyHabitTrackings(): List<HabitTracking> {
    return listOf(
        HabitTracking(
            id = "1",
            bookTitle = "Book Title 1",
            readingDate = Calendar.getInstance().apply {
                set(2026, Calendar.APRIL, 13)
            }.time,
            readingStartTime = "10:00",
            readingEndTime = "11:00",
            isSynchronized = true,
        ),
        HabitTracking(
            id = "2",
            bookTitle = "Book Title 2",
            readingDate = Calendar.getInstance().apply {
                set(2026, Calendar.APRIL, 13)
            }.time,
            readingStartTime = "13:00",
            readingEndTime = "13:30",
            isSynchronized = true,
        ),
        HabitTracking(
            id = "3",
            bookTitle = "Book Title 3",
            readingDate = Calendar.getInstance().apply {
                set(2026, Calendar.APRIL, 13)
            }.time,
            readingStartTime = "15:00",
            readingEndTime = "15:30",
            isSynchronized = false,
        )
    )
}