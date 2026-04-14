package id.invi.kread.data.remote

import id.invi.kread.data.remote.network.ApiService
import id.invi.kread.data.remote.network.request.AddTrackingRequest
import id.invi.kread.data.remote.network.request.LoginRequest
import id.invi.kread.data.remote.network.request.RegisterRequest
import id.invi.kread.data.remote.network.request.UpdateTrackingRequest
import id.invi.kread.data.remote.network.response.LoginResponse
import id.invi.kread.data.remote.network.response.RegisterResponse
import id.invi.kread.data.remote.network.response.TrackingResponse
import id.invi.kread.data.remote.network.safeCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun login(
        email: String,
        password: String,
    ): RemoteResult<LoginResponse> {
        return safeCall {
            val request = LoginRequest(email, password)
            apiService.login(request)
        }
    }

    suspend fun register(
        email: String,
        password: String,
    ): RemoteResult<RegisterResponse> {
        return safeCall {
            val request = RegisterRequest(email, password)
            apiService.register(request)
        }
    }

    suspend fun addTracking(
        token: String,
        request: AddTrackingRequest,
    ): RemoteResult<Unit> {
        return safeCall {
            apiService.addTracking("Bearer $token", request)
        }
    }

    suspend fun updateTracking(
        token: String,
        id: String,
        request: UpdateTrackingRequest,
    ): RemoteResult<Unit> {
        return safeCall {
            apiService.updateTracking(
                token = "Bearer $token",
                id = "eq.$id",
                body = request
            )
        }
    }

    suspend fun deleteTracking(
        token: String,
        id: String,
    ): RemoteResult<Unit> {
        return safeCall {
            apiService.deleteTracking(
                token = "Bearer $token",
                id = "eq.$id",
            )
        }
    }

    suspend fun getTrackings(
        token: String,
    ): RemoteResult<List<TrackingResponse>> {
        return safeCall {
            apiService.getTrackings("Bearer $token")
        }
    }
}
