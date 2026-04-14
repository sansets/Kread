package id.invi.kread.data.local

import id.invi.kread.data.local.room.AppRoomDatabase
import id.invi.kread.data.local.room.entity.HabitTrackingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    appRoomDatabase: AppRoomDatabase
) {

    private val habitTrackingDao = appRoomDatabase.habitTrackingDao()

    suspend fun insertHabitTracking(habitTracking: HabitTrackingEntity): LocalResult<Unit> {
        return try {
            habitTrackingDao.insert(habitTracking)
            LocalResult.Success(Unit)
        } catch (ex: Exception) {
            LocalResult.Error(ex)
        }
    }

    suspend fun insertAllHabitTrackings(habitTrackings: List<HabitTrackingEntity>): LocalResult<Unit> {
        return try {
            habitTrackingDao.insertAll(habitTrackings)
            LocalResult.Success(Unit)
        } catch (ex: Exception) {
            LocalResult.Error(ex)
        }
    }

    suspend fun updateHabitTracking(habitTracking: HabitTrackingEntity): LocalResult<Unit> {
        return try {
            habitTrackingDao.update(habitTracking)
            LocalResult.Success(Unit)
        } catch (ex: Exception) {
            LocalResult.Error(ex)
        }
    }

    suspend fun deleteHabitTracking(habitTracking: HabitTrackingEntity): LocalResult<Unit> {
        return try {
            habitTrackingDao.delete(habitTracking)
            LocalResult.Success(Unit)
        } catch (ex: Exception) {
            LocalResult.Error(ex)
        }
    }

    suspend fun getHabitTrackingById(id: String): LocalResult<HabitTrackingEntity?> {
        return try {
            val result = habitTrackingDao.getHabitTrackingById(id)
            LocalResult.Success(result)
        } catch (ex: Exception) {
            LocalResult.Error(ex)
        }
    }

    fun getAllHabitTrackings(): Flow<LocalResult<List<HabitTrackingEntity>>> {
        return habitTrackingDao.getAllHabitTrackings().map {
            LocalResult.Success(it)
        }
    }

    suspend fun getUnsyncedHabitTrackings(): LocalResult<List<HabitTrackingEntity>> {
        return try {
            val result = habitTrackingDao.getUnsyncedHabitTrackings()
            LocalResult.Success(result)
        } catch (ex: Exception) {
            LocalResult.Error(ex)
        }
    }

    suspend fun deleteAllHabitTrackings(): LocalResult<Unit> {
        return try {
            habitTrackingDao.deleteAll()
            LocalResult.Success(Unit)
        } catch (ex: Exception) {
            LocalResult.Error(ex)
        }
    }
}
