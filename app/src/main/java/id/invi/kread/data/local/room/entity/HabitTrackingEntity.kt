package id.invi.kread.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "habit_trackings")
data class HabitTrackingEntity(

    @PrimaryKey
    @ColumnInfo("id")
    @SerialName("id")
    val id: String,

    @ColumnInfo("book_title")
    @SerialName("book_title")
    val bookTitle: String,

    @ColumnInfo("reading_date")
    @SerialName("reading_date")
    val readingDate: String,

    @ColumnInfo("reading_start_time")
    @SerialName("reading_start_time")
    val readingStartTime: String,

    @ColumnInfo("reading_end_time")
    @SerialName("reading_end_time")
    val readingEndTime: String,

    @ColumnInfo("is_synchronized")
    @SerialName("is_synchronized")
    val isSynchronized: Boolean,

    @ColumnInfo("is_synchronizing")
    @SerialName("is_synchronizing")
    val isSynchronizing: Boolean = false,

    @ColumnInfo("is_deleted")
    @SerialName("is_deleted")
    val isDeleted: Boolean,
)
