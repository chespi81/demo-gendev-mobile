package cl.tinet.demobank.ui.login.domain

import org.junit.Assert.*
import org.junit.Test

class LoginResponseTest {
    
    @Test
    fun `GIVEN complete login response WHEN created THEN should contain all properties`() {
        // GIVEN & WHEN
        val response = LoginResponse(
            success = true,
            token = "test-token",
            message = "Success",
            userId = "user123",
            username = "testuser"
        )
        
        // THEN
        assertTrue(response.success)
        assertEquals("test-token", response.token)
        assertEquals("Success", response.message)
        assertEquals("user123", response.userId)
        assertEquals("testuser", response.username)
    }
    
    @Test
    fun `GIVEN failed login response WHEN created THEN should contain failure properties`() {
        // GIVEN & WHEN
        val response = LoginResponse(
            success = false,
            token = null,
            message = "Invalid credentials",
            userId = null,
            username = null
        )
        
        // THEN
        assertFalse(response.success)
        assertNull(response.token)
        assertEquals("Invalid credentials", response.message)
        assertNull(response.userId)
        assertNull(response.username)
    }
    
    @Test
    fun `GIVEN login response with minimal data WHEN created THEN should handle nulls correctly`() {
        // GIVEN & WHEN
        val response = LoginResponse(
            success = true,
            token = "minimal-token",
            message = null
        )
        
        // THEN
        assertTrue(response.success)
        assertEquals("minimal-token", response.token)
        assertNull(response.message)
        assertNull(response.userId)
        assertNull(response.username)
    }
    
    @Test
    fun `GIVEN two identical login responses WHEN compared THEN should be equal`() {
        // GIVEN
        val response1 = LoginResponse(
            success = true,
            token = "same-token",
            message = "same-message",
            userId = "same-user",
            username = "same-username"
        )
        
        val response2 = LoginResponse(
            success = true,
            token = "same-token",
            message = "same-message",
            userId = "same-user",
            username = "same-username"
        )
        
        // WHEN & THEN
        assertEquals(response1, response2)
        assertEquals(response1.hashCode(), response2.hashCode())
    }
    
    @Test
    fun `GIVEN two different login responses WHEN compared THEN should not be equal`() {
        // GIVEN
        val response1 = LoginResponse(
            success = true,
            token = "token1",
            message = "message1",
            userId = "user1",
            username = "username1"
        )
        
        val response2 = LoginResponse(
            success = false,
            token = "token2",
            message = "message2",
            userId = "user2",
            username = "username2"
        )
        
        // WHEN & THEN
        assertNotEquals(response1, response2)
    }
    
    @Test
    fun `GIVEN login response WHEN toString is called THEN should return proper string representation`() {
        // GIVEN
        val response = LoginResponse(
            success = true,
            token = "test-token",
            message = "test-message",
            userId = "test-user",
            username = "test-username"
        )
        
        // WHEN
        val result = response.toString()
        
        // THEN
        assertTrue(result.contains("success=true"))
        assertTrue(result.contains("token=test-token"))
        assertTrue(result.contains("message=test-message"))
        assertTrue(result.contains("userId=test-user"))
        assertTrue(result.contains("username=test-username"))
    }
    
    @Test
    fun `GIVEN login response with copy WHEN properties changed THEN should create new instance with changes`() {
        // GIVEN
        val originalResponse = LoginResponse(
            success = false,
            token = null,
            message = "Original failed",
            userId = null,
            username = null
        )
        
        // WHEN
        val copiedResponse = originalResponse.copy(
            success = true,
            token = "new-token",
            userId = "new-user"
        )
        
        // THEN
        assertTrue(copiedResponse.success)
        assertEquals("new-token", copiedResponse.token)
        assertEquals("Original failed", copiedResponse.message) // Unchanged
        assertEquals("new-user", copiedResponse.userId)
        assertNull(copiedResponse.username) // Unchanged
        
        // Original should be unchanged
        assertFalse(originalResponse.success)
        assertNull(originalResponse.token)
    }
}