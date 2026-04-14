package id.invi.kread.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.invi.kread.data.local.room.entity.HabitTrackingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitTrackingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitTracking: HabitTrackingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(habitTrackings: List<HabitTrackingEntity>)

    @Update
    suspend fun update(habitTracking: HabitTrackingEntity)

    @Delete
    suspend fun delete(habitTracking: HabitTrackingEntity)

    @Query("SELECT * FROM habit_trackings WHERE id = :id")
    suspend fun getHabitTrackingById(id: String): HabitTrackingEntity?

    @Query("SELECT * FROM habit_trackings WHERE is_deleted = 0 ORDER BY reading_date DESC")
    fun getAllHabitTrackings(): Flow<List<HabitTrackingEntity>>

    @Query("SELECT * FROM habit_trackings WHERE is_synchronized = 0")
    suspend fun getUnsyncedHabitTrackings(): List<HabitTrackingEntity>

    @Query("DELETE FROM habit_trackings")
    suspend fun deleteAll()
}
