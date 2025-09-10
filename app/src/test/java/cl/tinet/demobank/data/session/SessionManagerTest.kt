package cl.tinet.demobank.data.session

import android.content.Context
import android.content.SharedPreferences
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class SessionManagerTest {
    
    private val mockContext: Context = mockk()
    private val mockSharedPreferences: SharedPreferences = mockk()
    private val mockEditor: SharedPreferences.Editor = mockk()
    
    private lateinit var sessionManager: SessionManager
    
    @Before
    fun setup() {
        // Mock SharedPreferences behavior
        every { mockContext.getSharedPreferences(any(), any()) } returns mockSharedPreferences
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockEditor.putBoolean(any(), any()) } returns mockEditor
        every { mockEditor.putString(any(), any()) } returns mockEditor
        every { mockEditor.apply() } just Runs
        every { mockEditor.clear() } returns mockEditor
        
        sessionManager = SessionManager(mockContext)
    }
    
    @After
    fun tearDown() {
        clearAllMocks()
    }
    
    @Test
    fun `GIVEN no saved session WHEN isUserLoggedIn is called THEN should return false`() {
        // GIVEN
        every { mockSharedPreferences.getBoolean("is_logged_in", false) } returns false
        
        // WHEN
        val result = sessionManager.isUserLoggedIn()
        
        // THEN
        assertFalse(result)
        verify { mockSharedPreferences.getBoolean("is_logged_in", false) }
    }
    
    @Test
    fun `GIVEN saved session WHEN isUserLoggedIn is called THEN should return true`() {
        // GIVEN
        every { mockSharedPreferences.getBoolean("is_logged_in", false) } returns true
        
        // WHEN
        val result = sessionManager.isUserLoggedIn()
        
        // THEN
        assertTrue(result)
        verify { mockSharedPreferences.getBoolean("is_logged_in", false) }
    }
    
    @Test
    fun `GIVEN user session data WHEN saveUserSession is called THEN should save all data correctly`() {
        // GIVEN
        val token = "test-token-123"
        val username = "15.413.217-1"
        val userId = "user-456"
        
        // WHEN
        sessionManager.saveUserSession(token, username, userId)
        
        // THEN
        verify { mockEditor.putBoolean("is_logged_in", true) }
        verify { mockEditor.putString("user_token", token) }
        verify { mockEditor.putString("username", username) }
        verify { mockEditor.putString("user_id", userId) }
        verify { mockEditor.apply() }
    }
    
    @Test
    fun `GIVEN user session data without userId WHEN saveUserSession is called THEN should save without userId`() {
        // GIVEN
        val token = "test-token-123"
        val username = "15.413.217-1"
        
        // WHEN
        sessionManager.saveUserSession(token, username)
        
        // THEN
        verify { mockEditor.putBoolean("is_logged_in", true) }
        verify { mockEditor.putString("user_token", token) }
        verify { mockEditor.putString("username", username) }
        verify(exactly = 0) { mockEditor.putString("user_id", any()) }
        verify { mockEditor.apply() }
    }
    
    @Test
    fun `GIVEN saved token WHEN getUserToken is called THEN should return saved token`() {
        // GIVEN
        val expectedToken = "saved-token-789"
        every { mockSharedPreferences.getString("user_token", null) } returns expectedToken
        
        // WHEN
        val result = sessionManager.getUserToken()
        
        // THEN
        assertEquals(expectedToken, result)
        verify { mockSharedPreferences.getString("user_token", null) }
    }
    
    @Test
    fun `GIVEN no saved token WHEN getUserToken is called THEN should return null`() {
        // GIVEN
        every { mockSharedPreferences.getString("user_token", null) } returns null
        
        // WHEN
        val result = sessionManager.getUserToken()
        
        // THEN
        assertNull(result)
        verify { mockSharedPreferences.getString("user_token", null) }
    }
    
    @Test
    fun `GIVEN saved username WHEN getUsername is called THEN should return saved username`() {
        // GIVEN
        val expectedUsername = "15.413.217-1"
        every { mockSharedPreferences.getString("username", null) } returns expectedUsername
        
        // WHEN
        val result = sessionManager.getUsername()
        
        // THEN
        assertEquals(expectedUsername, result)
        verify { mockSharedPreferences.getString("username", null) }
    }
    
    @Test
    fun `GIVEN saved userId WHEN getUserId is called THEN should return saved userId`() {
        // GIVEN
        val expectedUserId = "user-123"
        every { mockSharedPreferences.getString("user_id", null) } returns expectedUserId
        
        // WHEN
        val result = sessionManager.getUserId()
        
        // THEN
        assertEquals(expectedUserId, result)
        verify { mockSharedPreferences.getString("user_id", null) }
    }
    
    @Test
    fun `GIVEN saved session WHEN clearSession is called THEN should clear all data`() {
        // GIVEN - SessionManager ya configurado
        
        // WHEN
        sessionManager.clearSession()
        
        // THEN
        verify { mockEditor.clear() }
        verify { mockEditor.apply() }
    }
    
    @Test
    fun `GIVEN user logged in WHEN getUserSessionData is called THEN should return complete session data`() {
        // GIVEN
        val token = "session-token"
        val username = "15.413.217-1"
        val userId = "user-789"
        
        every { mockSharedPreferences.getBoolean("is_logged_in", false) } returns true
        every { mockSharedPreferences.getString("user_token", null) } returns token
        every { mockSharedPreferences.getString("username", null) } returns username
        every { mockSharedPreferences.getString("user_id", null) } returns userId
        
        // WHEN
        val result = sessionManager.getUserSessionData()
        
        // THEN
        assertNotNull(result)
        assertEquals(token, result?.token)
        assertEquals(username, result?.username)
        assertEquals(userId, result?.userId)
    }
    
    @Test
    fun `GIVEN user not logged in WHEN getUserSessionData is called THEN should return null`() {
        // GIVEN
        every { mockSharedPreferences.getBoolean("is_logged_in", false) } returns false
        
        // WHEN
        val result = sessionManager.getUserSessionData()
        
        // THEN
        assertNull(result)
        verify { mockSharedPreferences.getBoolean("is_logged_in", false) }
    }
    
    @Test
    fun `GIVEN partial session data WHEN getUserSessionData is called THEN should return data with nulls`() {
        // GIVEN
        val token = "partial-token"
        every { mockSharedPreferences.getBoolean("is_logged_in", false) } returns true
        every { mockSharedPreferences.getString("user_token", null) } returns token
        every { mockSharedPreferences.getString("username", null) } returns null
        every { mockSharedPreferences.getString("user_id", null) } returns null
        
        // WHEN
        val result = sessionManager.getUserSessionData()
        
        // THEN
        assertNotNull(result)
        assertEquals(token, result?.token)
        assertNull(result?.username)
        assertNull(result?.userId)
    }
}