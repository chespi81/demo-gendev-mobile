package cl.tinet.demobank.ui.login.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    private val mockRepository: LoginRepository = mockk()
    
    private lateinit var useCase: LoginUseCase
    
    @Before
    fun setup() {
        useCase = LoginUseCase(mockRepository)
    }
    
    @After
    fun tearDown() {
        clearAllMocks()
    }
    
    @Test
    fun `GIVEN valid credentials WHEN execute is called THEN should return success response`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val expectedRequest = LoginRequest(username, password)
        val expectedResponse = LoginResponse(
            success = true,
            token = "token123",
            message = "Login successful",
            userId = "user456",
            username = "15.413.217-1"
        )
        
        coEvery { mockRepository.login(expectedRequest) } returns expectedResponse
        
        // WHEN
        val result = useCase.execute(username, password)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(expectedRequest) }
    }
    
    @Test
    fun `GIVEN invalid credentials WHEN execute is called THEN should return failure response`() = runTest {
        // GIVEN
        val username = "invaliduser"
        val password = "wrongpassword"
        val expectedRequest = LoginRequest(username, password)
        val expectedResponse = LoginResponse(
            success = false,
            token = null,
            message = "Invalid credentials",
            userId = null,
            username = null
        )
        
        coEvery { mockRepository.login(expectedRequest) } returns expectedResponse
        
        // WHEN
        val result = useCase.execute(username, password)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(expectedRequest) }
    }
    
    @Test
    fun `GIVEN empty username WHEN execute is called THEN should pass request to repository`() = runTest {
        // GIVEN
        val username = ""
        val password = "password123"
        val expectedRequest = LoginRequest(username, password)
        val expectedResponse = LoginResponse(
            success = false,
            token = null,
            message = "Username is required",
            userId = null,
            username = null
        )
        
        coEvery { mockRepository.login(expectedRequest) } returns expectedResponse
        
        // WHEN
        val result = useCase.execute(username, password)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(expectedRequest) }
    }
    
    @Test
    fun `GIVEN empty password WHEN execute is called THEN should pass request to repository`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = ""
        val expectedRequest = LoginRequest(username, password)
        val expectedResponse = LoginResponse(
            success = false,
            token = null,
            message = "Password is required",
            userId = null,
            username = null
        )
        
        coEvery { mockRepository.login(expectedRequest) } returns expectedResponse
        
        // WHEN
        val result = useCase.execute(username, password)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(expectedRequest) }
    }
    
    @Test
    fun `GIVEN repository throws exception WHEN execute is called THEN should propagate exception`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val expectedRequest = LoginRequest(username, password)
        val expectedException = Exception("Network error")
        
        coEvery { mockRepository.login(expectedRequest) } throws expectedException
        
        // WHEN & THEN
        try {
            useCase.execute(username, password)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Network error", e.message)
            coVerify { mockRepository.login(expectedRequest) }
        }
    }
    
    @Test
    fun `GIVEN special characters in credentials WHEN execute is called THEN should handle correctly`() = runTest {
        // GIVEN
        val username = "user@domain.com"
        val password = "pass!@#$%^&*()"
        val expectedRequest = LoginRequest(username, password)
        val expectedResponse = LoginResponse(
            success = true,
            token = "special-token",
            message = null,
            userId = "special-user",
            username = username
        )
        
        coEvery { mockRepository.login(expectedRequest) } returns expectedResponse
        
        // WHEN
        val result = useCase.execute(username, password)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(expectedRequest) }
    }
    
    @Test
    fun `GIVEN long credentials WHEN execute is called THEN should handle correctly`() = runTest {
        // GIVEN
        val username = "very".repeat(100) + "longusername"
        val password = "very".repeat(100) + "longpassword"
        val expectedRequest = LoginRequest(username, password)
        val expectedResponse = LoginResponse(
            success = false,
            token = null,
            message = "Credentials too long",
            userId = null,
            username = null
        )
        
        coEvery { mockRepository.login(expectedRequest) } returns expectedResponse
        
        // WHEN
        val result = useCase.execute(username, password)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(expectedRequest) }
    }
    
    @Test
    fun `GIVEN repository returns response WHEN execute is called THEN should return same response`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val expectedRequest = LoginRequest(username, password)
        val expectedResponse = LoginResponse(
            success = false,
            token = null,
            message = "Server error",
            userId = null,
            username = null
        )
        
        coEvery { mockRepository.login(expectedRequest) } returns expectedResponse
        
        // WHEN
        val result = useCase.execute(username, password)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(expectedRequest) }
    }
    
    @Test
    fun `GIVEN multiple calls WHEN execute is called THEN should handle each independently`() = runTest {
        // GIVEN
        val username1 = "user1"
        val password1 = "pass1"
        val request1 = LoginRequest(username1, password1)
        val response1 = LoginResponse(true, "token1", null, "id1", username1)
        
        val username2 = "user2"
        val password2 = "pass2"
        val request2 = LoginRequest(username2, password2)
        val response2 = LoginResponse(false, null, "Invalid", null, null)
        
        coEvery { mockRepository.login(request1) } returns response1
        coEvery { mockRepository.login(request2) } returns response2
        
        // WHEN
        val result1 = useCase.execute(username1, password1)
        val result2 = useCase.execute(username2, password2)
        
        // THEN
        assertEquals(response1, result1)
        assertEquals(response2, result2)
        coVerify { mockRepository.login(request1) }
        coVerify { mockRepository.login(request2) }
    }
}