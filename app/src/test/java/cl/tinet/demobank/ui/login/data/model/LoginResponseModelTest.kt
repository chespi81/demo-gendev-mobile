package cl.tinet.demobank.ui.login.data.model

import cl.tinet.demobank.ui.login.domain.LoginResponse
import org.junit.Assert.*
import org.junit.Test

class LoginResponseModelTest {
    
    @Test
    fun `GIVEN successful login response model WHEN toDomain is called THEN should map to domain correctly`() {
        // GIVEN
        val responseModel = LoginResponseModel(
            authenticated = true,
            token = "test-token-123",
            username = "15.413.217-1",
            ownerId = "owner-456",
            message = "Login successful",
            error = null
        )
        
        // WHEN
        val result = responseModel.toDomain()
        
        // THEN
        assertEquals(true, result.success)
        assertEquals("test-token-123", result.token)
        assertEquals("Login successful", result.message)
        assertEquals("owner-456", result.userId)
        assertEquals("15.413.217-1", result.username)
    }
    
    @Test
    fun `GIVEN failed login response model WHEN toDomain is called THEN should map to domain correctly`() {
        // GIVEN
        val responseModel = LoginResponseModel(
            authenticated = false,
            token = null,
            username = null,
            ownerId = null,
            message = null,
            error = "Invalid credentials"
        )
        
        // WHEN
        val result = responseModel.toDomain()
        
        // THEN
        assertEquals(false, result.success)
        assertNull(result.token)
        assertEquals("Invalid credentials", result.message)
        assertNull(result.userId)
        assertNull(result.username)
    }
    
    @Test
    fun `GIVEN response model with null authenticated WHEN toDomain is called THEN should default to false`() {
        // GIVEN
        val responseModel = LoginResponseModel(
            authenticated = null,
            token = "some-token",
            username = "username",
            ownerId = "owner",
            message = "message",
            error = null
        )
        
        // WHEN
        val result = responseModel.toDomain()
        
        // THEN
        assertEquals(false, result.success)
    }
    
    @Test
    fun `GIVEN response model with both message and error WHEN toDomain is called THEN should prefer message`() {
        // GIVEN
        val responseModel = LoginResponseModel(
            authenticated = false,
            token = null,
            username = null,
            ownerId = null,
            message = "Custom message",
            error = "Error message"
        )
        
        // WHEN
        val result = responseModel.toDomain()
        
        // THEN
        assertEquals("Custom message", result.message)
    }
    
    @Test
    fun `GIVEN response model with null message but error WHEN toDomain is called THEN should use error`() {
        // GIVEN
        val responseModel = LoginResponseModel(
            authenticated = false,
            token = null,
            username = null,
            ownerId = null,
            message = null,
            error = "Error message"
        )
        
        // WHEN
        val result = responseModel.toDomain()
        
        // THEN
        assertEquals("Error message", result.message)
    }
    
    @Test
    fun `GIVEN response model with all null fields WHEN toDomain is called THEN should handle gracefully`() {
        // GIVEN
        val responseModel = LoginResponseModel(
            authenticated = null,
            token = null,
            username = null,
            ownerId = null,
            message = null,
            error = null
        )
        
        // WHEN
        val result = responseModel.toDomain()
        
        // THEN
        assertEquals(false, result.success)
        assertNull(result.token)
        assertNull(result.message)
        assertNull(result.userId)
        assertNull(result.username)
    }
    
    @Test
    fun `GIVEN response model with empty strings WHEN toDomain is called THEN should preserve empty strings`() {
        // GIVEN
        val responseModel = LoginResponseModel(
            authenticated = true,
            token = "",
            username = "",
            ownerId = "",
            message = "",
            error = ""
        )
        
        // WHEN
        val result = responseModel.toDomain()
        
        // THEN
        assertEquals(true, result.success)
        assertEquals("", result.token)
        assertEquals("", result.message)
        assertEquals("", result.userId)
        assertEquals("", result.username)
    }
}