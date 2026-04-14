package id.invi.kread.data

import id.invi.kread.data.local.LocalDataSource
import id.invi.kread.data.local.datastore.SessionManager
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
        emit(Result.Success(Unit))
    }

    override fun addTracking(habitTracking: HabitTracking): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        val token = sessionManager.accessToken.first() ?: ""
        val request = habitTracking.toRequest()
        val result = when (val remoteResult = remoteDataSource.addTracking(token, request)) {
            is RemoteResult.Success -> Result.Success(Unit)
            is RemoteResult.Error -> Result.Error(remoteResult.exception)
        }
        emit(result)
    }

    override fun updateTracking(habitTracking: HabitTracking): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        val token = sessionManager.accessToken.first() ?: ""
        val request = habitTracking.toUpdateRequest()
        val result = when (
            val remoteResult =
                remoteDataSource.updateTracking(token, habitTracking.id, request)
        ) {
            is RemoteResult.Success -> Result.Success(Unit)
            is RemoteResult.Error -> Result.Error(remoteResult.exception)
        }
        emit(result)
    }

    override fun deleteTracking(id: String): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        val token = sessionManager.accessToken.first() ?: ""
        val result =
            when (val remoteResult = remoteDataSource.deleteTracking(token, id)) {
                is RemoteResult.Success -> Result.Success(Unit)
                is RemoteResult.Error -> Result.Error(remoteResult.exception)
            }
        emit(result)
    }

    override fun getTrackings(): Flow<Result<List<HabitTracking>>> = flow {
        emit(Result.Loading)
        val token = sessionManager.accessToken.first() ?: ""
        val result = when (val remoteResult = remoteDataSource.getTrackings(token)) {
            is RemoteResult.Success -> Result.Success(remoteResult.data.toDomain())
            is RemoteResult.Error -> Result.Error(remoteResult.exception)
        }
        emit(result)
    }
}
