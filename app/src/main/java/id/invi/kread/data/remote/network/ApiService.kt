package id.invi.kread.data.remote.network

import id.invi.kread.data.remote.network.request.AddTrackingRequest
import id.invi.kread.data.remote.network.request.LoginRequest
import id.invi.kread.data.remote.network.request.RegisterRequest
import id.invi.kread.data.remote.network.request.UpdateTrackingRequest
import id.invi.kread.data.remote.network.response.LoginResponse
import id.invi.kread.data.remote.network.response.RegisterResponse
import id.invi.kread.data.remote.network.response.TrackingResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("auth/v1/signup")
    suspend fun register(
        @Body body: RegisterRequest,
    ): Response<RegisterResponse>

    @POST("auth/v1/token?grant_type=password")
    suspend fun login(
        @Body body: LoginRequest,
    ): Response<LoginResponse>

    @Headers("Prefer: resolution=merge-duplicates")
    @POST("rest/v1/tracking")
    suspend fun addTracking(
        @Header("Authorization") token: String,
        @Body body: AddTrackingRequest,
    ): Response<Unit>

    @PATCH("rest/v1/tracking")
    suspend fun updateTracking(
        @Header("Authorization") token: String,
        @Query("id") id: String,
        @Body body: UpdateTrackingRequest,
    ): Response<Unit>

    @DELETE("rest/v1/tracking")
    suspend fun deleteTracking(
        @Header("Authorization") token: String,
        @Query("id") id: String,
    ): Response<Unit>

    @GET("rest/v1/tracking?order=reading_date.asc,reading_start_time.asc")
    suspend fun getTrackings(
        @Header("Authorization") token: String,
    ): Response<List<TrackingResponse>>
}