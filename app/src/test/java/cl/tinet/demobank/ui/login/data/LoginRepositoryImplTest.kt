package cl.tinet.demobank.ui.login.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.tinet.demobank.ui.login.domain.LoginRequest
import cl.tinet.demobank.ui.login.domain.LoginResponse
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginRepositoryImplTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    private val mockDataSource: LoginDataSource = mockk()
    
    private lateinit var repository: LoginRepositoryImpl
    
    @Before
    fun setup() {
        repository = LoginRepositoryImpl(mockDataSource)
    }
    
    @After
    fun tearDown() {
        clearAllMocks()
    }
    
    @Test
    fun `GIVEN valid login request WHEN login is called THEN should delegate to data source`() = runTest {
        // GIVEN
        val request = LoginRequest("testuser", "password123")
        val expectedResponse = LoginResponse(
            success = true,
            token = "token123",
            message = "Success",
            userId = "user456",
            username = "15.413.217-1"
        )
        
        coEvery { mockDataSource.login(request) } returns expectedResponse
        
        // WHEN
        val result = repository.login(request)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockDataSource.login(request) }
    }
    
    @Test
    fun `GIVEN invalid login request WHEN login is called THEN should return failure response`() = runTest {
        // GIVEN
        val request = LoginRequest("invaliduser", "wrongpassword")
        val expectedResponse = LoginResponse(
            success = false,
            token = null,
            message = "Invalid credentials",
            userId = null,
            username = null
        )
        
        coEvery { mockDataSource.login(request) } returns expectedResponse
        
        // WHEN
        val result = repository.login(request)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockDataSource.login(request) }
    }
    
    @Test
    fun `GIVEN data source throws exception WHEN login is called THEN should propagate exception`() = runTest {
        // GIVEN
        val request = LoginRequest("testuser", "password123")
        val expectedException = Exception("Network error")
        
        coEvery { mockDataSource.login(request) } throws expectedException
        
        // WHEN & THEN
        try {
            repository.login(request)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Network error", e.message)
            coVerify { mockDataSource.login(request) }
        }
    }
    
    @Test
    fun `GIVEN empty credentials WHEN login is called THEN should pass to data source`() = runTest {
        // GIVEN
        val request = LoginRequest("", "")
        val expectedResponse = LoginResponse(
            success = false,
            token = null,
            message = "Empty credentials",
            userId = null,
            username = null
        )
        
        coEvery { mockDataSource.login(request) } returns expectedResponse
        
        // WHEN
        val result = repository.login(request)
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockDataSource.login(request) }
    }
    
    @Test
    fun `GIVEN multiple login attempts WHEN login is called THEN should handle each independently`() = runTest {
        // GIVEN
        val request1 = LoginRequest("user1", "pass1")
        val request2 = LoginRequest("user2", "pass2")
        
        val response1 = LoginResponse(true, "token1", null, "id1", "user1")
        val response2 = LoginResponse(false, null, "Invalid", null, null)
        
        coEvery { mockDataSource.login(request1) } returns response1
        coEvery { mockDataSource.login(request2) } returns response2
        
        // WHEN
        val result1 = repository.login(request1)
        val result2 = repository.login(request2)
        
        // THEN
        assertEquals(response1, result1)
        assertEquals(response2, result2)
        coVerify { mockDataSource.login(request1) }
        coVerify { mockDataSource.login(request2) }
    }
}