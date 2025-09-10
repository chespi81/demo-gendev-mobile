package cl.tinet.demobank

import cl.tinet.demobank.data.session.SessionManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashActivityTest {

    @MockK(relaxed = true)
    lateinit var mockSessionManager: SessionManager

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN valid SessionManager WHEN isUserLoggedIn returns true THEN session check should be successful`() {
        // GIVEN
        every { mockSessionManager.isUserLoggedIn() } returns true

        // WHEN
        val result = mockSessionManager.isUserLoggedIn()

        // THEN
        assert(result)
        verify { mockSessionManager.isUserLoggedIn() }
    }

    @Test
    fun `GIVEN valid SessionManager WHEN isUserLoggedIn returns false THEN session check should be unsuccessful`() {
        // GIVEN
        every { mockSessionManager.isUserLoggedIn() } returns false

        // WHEN
        val result = mockSessionManager.isUserLoggedIn()

        // THEN
        assert(!result)
        verify { mockSessionManager.isUserLoggedIn() }
    }

    @Test
    fun `GIVEN SessionManager with logged user WHEN session check is performed THEN should return true`() {
        // GIVEN
        every { mockSessionManager.isUserLoggedIn() } returns true

        // WHEN
        val isLoggedIn = mockSessionManager.isUserLoggedIn()

        // THEN
        assert(isLoggedIn)
        verify(exactly = 1) { mockSessionManager.isUserLoggedIn() }
    }

    @Test
    fun `GIVEN SessionManager without logged user WHEN session check is performed THEN should return false`() {
        // GIVEN
        every { mockSessionManager.isUserLoggedIn() } returns false

        // WHEN
        val isLoggedIn = mockSessionManager.isUserLoggedIn()

        // THEN
        assert(!isLoggedIn)
        verify(exactly = 1) { mockSessionManager.isUserLoggedIn() }
    }
}