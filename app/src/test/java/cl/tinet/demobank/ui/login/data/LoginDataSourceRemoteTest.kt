package cl.tinet.demobank.ui.login.data

import cl.tinet.demobank.ui.login.data.api.LoginApi
import cl.tinet.demobank.ui.login.data.model.LoginRequestModel
import cl.tinet.demobank.ui.login.data.model.LoginResponseModel
import cl.tinet.demobank.ui.login.domain.LoginRequest
import cl.tinet.demobank.ui.login.domain.LoginResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginDataSourceRemoteTest {

    @MockK(relaxed = true)
    lateinit var mockApi: LoginApi

    private lateinit var dataSource: LoginDataSourceRemote

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dataSource = LoginDataSourceRemote(mockApi)
    }

    @Test
    fun `GIVEN successful api response WHEN login is called THEN should return successful LoginResponse`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val request = LoginRequest(username, password)
        val requestModel = LoginRequestModel(username, password)
        
        val responseModel = LoginResponseModel(
            authenticated = true,
            token = "test-token-123",
            username = username,
            ownerId = "user123",
            message = null,
            error = null
        )
        
        val mockResponse = mockk<Response<LoginResponseModel>> {
            every { isSuccessful } returns true
            every { body() } returns responseModel
        }
        
        coEvery { mockApi.login(requestModel) } returns mockResponse

        // WHEN
        val result = dataSource.login(request)

        // THEN
        assert(result.success)
        assert(result.token == "test-token-123")
        assert(result.username == username)
        assert(result.userId == "user123")
        coVerify { mockApi.login(requestModel) }
    }

    @Test
    fun `GIVEN successful api response with null body WHEN login is called THEN should return failure with empty response message`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val request = LoginRequest(username, password)
        val requestModel = LoginRequestModel(username, password)
        
        val mockResponse = mockk<Response<LoginResponseModel>> {
            every { isSuccessful } returns true
            every { body() } returns null
        }
        
        coEvery { mockApi.login(requestModel) } returns mockResponse

        // WHEN
        val result = dataSource.login(request)

        // THEN
        assert(!result.success)
        assert(result.token == null)
        assert(result.message == "Empty response from server")
        coVerify { mockApi.login(requestModel) }
    }

    @Test
    fun `GIVEN failed api response WHEN login is called THEN should return failure with HTTP error message`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "wrongpassword"
        val request = LoginRequest(username, password)
        val requestModel = LoginRequestModel(username, password)
        
        val mockResponse = mockk<Response<LoginResponseModel>> {
            every { isSuccessful } returns false
            every { code() } returns 401
            every { message() } returns "Unauthorized"
        }
        
        coEvery { mockApi.login(requestModel) } returns mockResponse

        // WHEN
        val result = dataSource.login(request)

        // THEN
        assert(!result.success)
        assert(result.token == null)
        assert(result.message == "HTTP 401: Unauthorized")
        coVerify { mockApi.login(requestModel) }
    }

    @Test
    fun `GIVEN network exception WHEN login is called THEN should return failure with network error message`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val request = LoginRequest(username, password)
        val requestModel = LoginRequestModel(username, password)
        
        val networkException = RuntimeException("Network connection failed")
        coEvery { mockApi.login(requestModel) } throws networkException

        // WHEN
        val result = dataSource.login(request)

        // THEN
        assert(!result.success)
        assert(result.token == null)
        assert(result.message == "Network connection failed")
        coVerify { mockApi.login(requestModel) }
    }

    @Test
    fun `GIVEN exception with null message WHEN login is called THEN should return failure with generic error message`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val request = LoginRequest(username, password)
        val requestModel = LoginRequestModel(username, password)
        
        val exception = RuntimeException(null as String?)
        coEvery { mockApi.login(requestModel) } throws exception

        // WHEN
        val result = dataSource.login(request)

        // THEN
        assert(!result.success)
        assert(result.token == null)
        assert(result.message == "Network error occurred")
        coVerify { mockApi.login(requestModel) }
    }

    @Test
    fun `GIVEN successful api response with error field WHEN login is called THEN should return response with error as message`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val request = LoginRequest(username, password)
        val requestModel = LoginRequestModel(username, password)
        
        val responseModel = LoginResponseModel(
            authenticated = false,
            token = null,
            username = null,
            ownerId = null,
            message = null,
            error = "Invalid credentials"
        )
        
        val mockResponse = mockk<Response<LoginResponseModel>> {
            every { isSuccessful } returns true
            every { body() } returns responseModel
        }
        
        coEvery { mockApi.login(requestModel) } returns mockResponse

        // WHEN
        val result = dataSource.login(request)

        // THEN
        assert(!result.success)
        assert(result.token == null)
        assert(result.message == "Invalid credentials")
        coVerify { mockApi.login(requestModel) }
    }

    @Test
    fun `GIVEN successful api response with both message and error WHEN login is called THEN should prioritize message over error`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val request = LoginRequest(username, password)
        val requestModel = LoginRequestModel(username, password)
        
        val responseModel = LoginResponseModel(
            authenticated = true,
            token = "test-token-123",
            username = username,
            ownerId = "user123",
            message = "Login successful",
            error = "This should be ignored"
        )
        
        val mockResponse = mockk<Response<LoginResponseModel>> {
            every { isSuccessful } returns true
            every { body() } returns responseModel
        }
        
        coEvery { mockApi.login(requestModel) } returns mockResponse

        // WHEN
        val result = dataSource.login(request)

        // THEN
        assert(result.success)
        assert(result.token == "test-token-123")
        assert(result.message == "Login successful") // message should be prioritized over error
        coVerify { mockApi.login(requestModel) }
    }
}