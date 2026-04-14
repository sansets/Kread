package id.invi.kread.data

import app.cash.turbine.test
import id.invi.kread.data.local.LocalDataSource
import id.invi.kread.data.local.LocalResult
import id.invi.kread.data.local.datastore.SessionManager
import id.invi.kread.data.local.room.entity.HabitTrackingEntity
import id.invi.kread.data.remote.RemoteDataSource
import id.invi.kread.data.remote.RemoteResult
import id.invi.kread.data.remote.network.response.LoginResponse
import id.invi.kread.data.remote.network.response.RegisterResponse
import id.invi.kread.domain.Result
import id.invi.kread.domain.model.HabitTracking
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class AppRepositoryImplTest {

    private lateinit var repository: AppRepositoryImpl
    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private val sessionManager: SessionManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        repository = AppRepositoryImpl(remoteDataSource, localDataSource, sessionManager)
    }

    @Test
    fun `checkIsLoggedIn returns true when token exists`() = runTest {
        coEvery { sessionManager.accessToken } returns flowOf("valid_token")

        repository.checkIsLoggedIn().test {
            val result = awaitItem()
            assert(result is Result.Success && result.data)
            awaitComplete()
        }
    }

    @Test
    fun `register and login success flow`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val registerResponse = RegisterResponse(id = "1", email = email)
        val loginResponse = LoginResponse(accessToken = "token", user = null)

        coEvery { remoteDataSource.register(email, password) } returns RemoteResult.Success(registerResponse)
        coEvery { remoteDataSource.login(email, password) } returns RemoteResult.Success(loginResponse)

        repository.register(email, password).test {
            assertEquals(Result.Loading, awaitItem())
            val successResult = awaitItem()
            assert(successResult is Result.Success)
            awaitComplete()
        }
        io.mockk.coVerify { sessionManager.saveAccessToken("token") }
    }

    @Test
    fun `login returns success when remote login is successful`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val loginResponse = LoginResponse(accessToken = "token", user = null)

        coEvery { remoteDataSource.login(email, password) } returns RemoteResult.Success(loginResponse)

        repository.login(email, password).test {
            assertEquals(Result.Loading, awaitItem())
            val successResult = awaitItem()
            assert(successResult is Result.Success)
            awaitComplete()
        }
    }

    @Test
    fun `logout clears session and local data`() = runTest {
        coEvery { localDataSource.deleteAllHabitTrackings() } returns LocalResult.Success(Unit)

        repository.logout().test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(Unit), awaitItem())
            awaitComplete()
        }
        io.mockk.coVerify { sessionManager.clearSession() }
        io.mockk.coVerify { localDataSource.deleteAllHabitTrackings() }
    }

    @Test
    fun `addTracking saves to local first (offline-first) and then syncs`() = runTest {
        val habitTracking = HabitTracking(
            id = "test-id",
            bookTitle = "Test Book",
            readingDate = Date(),
            readingStartTime = "10:00",
            readingEndTime = "11:00",
            isSynchronized = false
        )
        
        coEvery { localDataSource.insertHabitTracking(any()) } returns LocalResult.Success(Unit)
        coEvery { sessionManager.accessToken } returns flowOf("token")
        coEvery { remoteDataSource.addTracking(any(), any()) } returns RemoteResult.Success(Unit)

        repository.addTracking(habitTracking).test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(Unit), awaitItem()) // Local save success
            awaitComplete()
        }

        // Verify local first
        io.mockk.coVerify(exactly = 1) { 
            localDataSource.insertHabitTracking(match { !it.isSynchronized }) 
        }
        // Verify remote sync
        io.mockk.coVerify(exactly = 1) { remoteDataSource.addTracking("token", any()) }
        // Verify local update after sync
        io.mockk.coVerify(exactly = 1) { 
            localDataSource.insertHabitTracking(match { it.isSynchronized }) 
        }
    }

    @Test
    fun `getTrackings starts with loading and triggers sync`() = runTest {
        coEvery { localDataSource.getAllHabitTrackings() } returns flowOf(LocalResult.Success(emptyList<HabitTrackingEntity>()))
        coEvery { sessionManager.accessToken } returns flowOf("token")
        coEvery { localDataSource.getUnsyncedHabitTrackings() } returns LocalResult.Success(emptyList())
        coEvery { remoteDataSource.getTrackings("token") } returns RemoteResult.Success(emptyList())
        coEvery { localDataSource.insertAllHabitTrackings(any()) } returns LocalResult.Success(Unit)

        repository.getTrackings().test {
            assertEquals(Result.Loading, awaitItem())
            val result = awaitItem()
            assert(result is Result.Success)
            cancelAndIgnoreRemainingEvents()
        }

        io.mockk.coVerify { remoteDataSource.getTrackings("token") }
    }
}
