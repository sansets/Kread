package id.invi.kread.data

import id.invi.kread.data.local.LocalDataSource
import id.invi.kread.data.local.LocalResult
import id.invi.kread.data.local.datastore.SessionManager
import id.invi.kread.data.local.mapper.toDomain
import id.invi.kread.data.local.mapper.toEntity
import id.invi.kread.data.remote.RemoteDataSource
import id.invi.kread.data.remote.RemoteResult
import id.invi.kread.data.remote.network.mapper.toDomain
import id.invi.kread.data.remote.network.mapper.toRequest
import id.invi.kread.data.remote.network.mapper.toUpdateRequest
import id.invi.kread.domain.AppRepository
import id.invi.kread.domain.Result
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sessionManager: SessionManager,
) : AppRepository {

    override fun checkIsLoggedIn(): Flow<Result<Boolean>> =
        sessionManager.accessToken.map { token ->
            Result.Success(!token.isNullOrEmpty())
        }

    override fun register(
        email: String,
        password: String,
    ): Flow<Result<User>> = flow {
        emit(Result.Loading)
        val remoteResult = remoteDataSource.register(
            email = email,
            password = password,
        )
        val result = when (remoteResult) {
            is RemoteResult.Success -> {
                when (val loginResult = remoteDataSource.login(email, password)) {
                    is RemoteResult.Success -> {
                        loginResult.data.accessToken?.let {
                            sessionManager.saveAccessToken(it)
                        }
                        Result.Success(loginResult.data.toDomain())
                    }

                    is RemoteResult.Error -> Result.Error(loginResult.exception)
                }
            }

            is RemoteResult.Error -> {
                Result.Error(remoteResult.exception)
            }
        }
        emit(result)
    }

    override fun login(
        email: String,
        password: String
    ): Flow<Result<User>> = flow {
        emit(Result.Loading)
        val result = when (val remoteResult = remoteDataSource.login(
            email = email,
            password = password,
        )) {
            is RemoteResult.Success -> {
                remoteResult.data.accessToken?.let {
                    sessionManager.saveAccessToken(it)
                }
                Result.Success(remoteResult.data.toDomain())
            }

            is RemoteResult.Error -> {
                Result.Error(remoteResult.exception)
            }
        }
        emit(result)
    }

    override fun logout(): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        sessionManager.clearSession()
        localDataSource.deleteAllHabitTrackings()
        emit(Result.Success(Unit))
    }

    override fun addTracking(habitTracking: HabitTracking): Flow<Result<Unit>> = flow {
        emit(Result.Loading)

        // generate id for both local and remote
        val id = java.util.UUID.randomUUID().toString()

        // Save to local first
        val entity = habitTracking.toEntity(
            isSynchronizing = true,
            isSynchronized = false,
        ).copy(id = id)
        localDataSource.insertHabitTracking(entity)
        emit(Result.Success(Unit))

        //  Try to sync with remote
        val token = sessionManager.accessToken.first() ?: ""
        val request = habitTracking.toRequest().copy(id = id)
        when (remoteDataSource.addTracking(token, request)) {
            is RemoteResult.Success -> {
                localDataSource.insertHabitTracking(
                    entity.copy(
                        isSynchronized = true,
                        isSynchronizing = false,
                    )
                )
            }

            is RemoteResult.Error -> {
                localDataSource.insertHabitTracking(
                    entity.copy(
                        isSynchronized = false,
                        isSynchronizing = false,
                    )
                )
            }
        }
    }

    override fun updateTracking(habitTracking: HabitTracking): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        // Update local first
        val entity = habitTracking.toEntity(
            isSynchronized = false,
            isSynchronizing = true,
        )
        localDataSource.updateHabitTracking(entity)
        emit(Result.Success(Unit))

        // Try to sync with remote
        val token = sessionManager.accessToken.first() ?: ""
        val request = habitTracking.toUpdateRequest()
        when (remoteDataSource.updateTracking(token, habitTracking.id, request)) {
            is RemoteResult.Success -> {
                localDataSource.updateHabitTracking(
                    entity.copy(
                        isSynchronized = true,
                        isSynchronizing = false,
                    )
                )
            }

            is RemoteResult.Error -> {
                localDataSource.updateHabitTracking(
                    entity.copy(
                        isSynchronized = false,
                        isSynchronizing = false,
                    )
                )
            }
        }
    }

    override fun deleteTracking(id: String): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        // Mark as deleted in local
        val localResult = localDataSource.getHabitTrackingById(id)
        if (localResult is LocalResult.Success && localResult.data != null) {
            val entity = localResult.data
            val updatedEntity = entity.copy(isDeleted = true, isSynchronized = true)
            localDataSource.updateHabitTracking(updatedEntity)
            emit(Result.Success(Unit))

            // Try to delete from remote
            val token = sessionManager.accessToken.first() ?: ""
            when (remoteDataSource.deleteTracking(token, id)) {
                is RemoteResult.Success -> {
                    localDataSource.deleteHabitTracking(updatedEntity)
                }

                is RemoteResult.Error -> {
                    localDataSource.updateHabitTracking(updatedEntity.copy(isSynchronized = false))
                }
            }
        } else {
            emit(Result.Error(Exception("Tracking not found")))
        }
    }

    override fun getTrackings(): Flow<Result<List<HabitTracking>>> =
        localDataSource.getAllHabitTrackings()
            .map { localResult ->
                when (localResult) {
                    is LocalResult.Success -> Result.Success(localResult.data.toDomain())
                    is LocalResult.Error -> Result.Error(localResult.exception)
                }
            }
            .onStart {
                emit(Result.Loading)
                syncTrackings()
            }
            .catch { e ->
                Timber.e(e)
                emit(Result.Error(e as? Exception ?: Exception(e)))
            }

    private suspend fun syncTrackings() {
        val token = sessionManager.accessToken.first() ?: return

        // Upload unsynced items
        val unsyncedResult = localDataSource.getUnsyncedHabitTrackings()
        if (unsyncedResult is LocalResult.Success) {
            unsyncedResult.data.forEach { entity ->
                // Mark as synchronizing
                localDataSource.updateHabitTracking(entity.copy(isSynchronizing = true))

                if (entity.isDeleted) {
                    // Sync deletion
                    if (remoteDataSource.deleteTracking(token, entity.id) is RemoteResult.Success) {
                        localDataSource.deleteHabitTracking(entity)
                    } else {
                        localDataSource.updateHabitTracking(entity.copy(isSynchronizing = false))
                    }
                } else {
                    // Sync addition or update
                    val request = entity.toDomain().toRequest()
                    // Upsert
                    if (remoteDataSource.addTracking(token, request) is RemoteResult.Success) {
                        localDataSource.updateHabitTracking(
                            entity.copy(
                                isSynchronized = true,
                                isSynchronizing = false
                            )
                        )
                    } else {
                        localDataSource.updateHabitTracking(entity.copy(isSynchronizing = false))
                    }
                }
            }
        }

        // Fetch from remote and update local
        when (val remoteResult = remoteDataSource.getTrackings(token)) {
            is RemoteResult.Success -> {
                val remoteData = remoteResult.data.toDomain()
                localDataSource.insertAllHabitTrackings(remoteData.map { it.toEntity(isSynchronized = true) })
            }

            is RemoteResult.Error -> {}
        }
    }
}
