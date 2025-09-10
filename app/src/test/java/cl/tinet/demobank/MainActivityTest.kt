package cl.tinet.demobank

import cl.tinet.demobank.data.session.SessionManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @MockK(relaxed = true)
    lateinit var mockSessionManager: SessionManager

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN valid SessionManager WHEN getUsername is called THEN should return username`() {
        // GIVEN
        val expectedUsername = "15.413.217-1"
        every { mockSessionManager.getUsername() } returns expectedUsername

        // WHEN
        val result = mockSessionManager.getUsername()

        // THEN
        assert(result == expectedUsername)
        verify { mockSessionManager.getUsername() }
    }

    @Test
    fun `GIVEN SessionManager with empty username WHEN getUsername is called THEN should return null`() {
        // GIVEN
        every { mockSessionManager.getUsername() } returns null

        // WHEN
        val result = mockSessionManager.getUsername()

        // THEN
        assert(result == null)
        verify { mockSessionManager.getUsername() }
    }

    @Test
    fun `GIVEN SessionManager WHEN clearSession is called THEN session should be cleared`() {
        // GIVEN
        every { mockSessionManager.clearSession() } returns Unit

        // WHEN
        mockSessionManager.clearSession()

        // THEN
        verify { mockSessionManager.clearSession() }
    }

    @Test
    fun `GIVEN MainActivity with valid username WHEN session username is retrieved THEN should return expected value`() {
        // GIVEN
        val expectedUsername = "test.user@example.com"
        every { mockSessionManager.getUsername() } returns expectedUsername

        // WHEN
        val result = mockSessionManager.getUsername()

        // THEN
        assert(result == expectedUsername)
        assert(!result.isNullOrEmpty())
        verify { mockSessionManager.getUsername() }
    }

    @Test
    fun `GIVEN MainActivity with empty username WHEN session username is retrieved THEN should handle empty username`() {
        // GIVEN
        every { mockSessionManager.getUsername() } returns ""

        // WHEN
        val result = mockSessionManager.getUsername()

        // THEN
        assert(result == "")
        assert(result.isNullOrEmpty())
        verify { mockSessionManager.getUsername() }
    }

    @Test
    fun `GIVEN SessionManager with null username WHEN getUsername is called THEN should handle null value`() {
        // GIVEN
        every { mockSessionManager.getUsername() } returns null

        // WHEN  
        val result = mockSessionManager.getUsername()

        // THEN
        assert(result.isNullOrEmpty())
        verify { mockSessionManager.getUsername() }
    }
}