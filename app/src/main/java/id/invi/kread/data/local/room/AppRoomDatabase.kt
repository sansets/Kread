package id.invi.kread.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.invi.kread.data.local.room.dao.HabitTrackingDao
import id.invi.kread.data.local.room.entity.HabitTrackingEntity

@Database(
    entities = [
        HabitTrackingEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun habitTrackingDao(): HabitTrackingDao
}