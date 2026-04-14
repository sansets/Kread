package id.invi.kread.domain

import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun checkIsLoggedIn(): Flow<Result<Boolean>>

    fun register(email: String, password: String): Flow<Result<User>>

    fun login(email: String, password: String): Flow<Result<User>>

    fun logout(): Flow<Result<Unit>>

    fun addTracking(habitTracking: HabitTracking): Flow<Result<Unit>>

    fun updateTracking(habitTracking: HabitTracking): Flow<Result<Unit>>

    fun deleteTracking(id: String): Flow<Result<Unit>>

    fun getTrackings(): Flow<Result<List<HabitTracking>>>
}