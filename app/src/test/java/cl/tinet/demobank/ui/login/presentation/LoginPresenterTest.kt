package cl.tinet.demobank.ui.login.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.tinet.demobank.data.session.SessionManager
import cl.tinet.demobank.ui.login.domain.LoginResponse
import cl.tinet.demobank.ui.login.domain.LoginUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginPresenterTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    private val testDispatcher = StandardTestDispatcher()
    
    private val mockView: LoginContract.View = mockk(relaxed = true)
    private val mockUseCase: LoginUseCase = mockk()
    private val mockSessionManager: SessionManager = mockk(relaxed = true)
    
    private lateinit var presenter: LoginPresenter
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        presenter = LoginPresenter(mockUseCase, mockSessionManager)
        presenter.attachView(mockView)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
    
    @Test
    fun `GIVEN empty username WHEN login is called THEN should show validation error`() {
        // GIVEN
        val emptyUsername = ""
        val validPassword = "password123"
        
        // WHEN
        presenter.login(emptyUsername, validPassword)
        
        // THEN
        verify { mockView.showLoginError("Usuario y contraseña son requeridos") }
        verify(exactly = 0) { mockView.showLoading() }
    }
    
    @Test
    fun `GIVEN empty password WHEN login is called THEN should show validation error`() {
        // GIVEN
        val validUsername = "testuser"
        val emptyPassword = ""
        
        // WHEN
        presenter.login(validUsername, emptyPassword)
        
        // THEN
        verify { mockView.showLoginError("Usuario y contraseña son requeridos") }
        verify(exactly = 0) { mockView.showLoading() }
    }
    
    @Test
    fun `GIVEN both fields empty WHEN login is called THEN should show validation error`() {
        // GIVEN
        val emptyUsername = ""
        val emptyPassword = ""
        
        // WHEN
        presenter.login(emptyUsername, emptyPassword)
        
        // THEN
        verify { mockView.showLoginError("Usuario y contraseña son requeridos") }
        verify(exactly = 0) { mockView.showLoading() }
    }
    
    @Test
    fun `GIVEN successful login response WHEN login is called THEN should save session and navigate`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val serverUsername = "15.413.217-1"
        val successResponse = LoginResponse(
            success = true,
            token = "token123",
            message = null,
            userId = "user123",
            username = serverUsername
        )
        
        coEvery { mockUseCase.execute(username, password) } returns successResponse
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockSessionManager.saveUserSession("token123", serverUsername, "user123") }
        verify { mockView.navigateToHome() }
        verify(exactly = 0) { mockView.showLoginError(any()) }
    }
    
    @Test
    fun `GIVEN successful login without server username WHEN login is called THEN should use form username`() = runTest {
        // GIVEN
        val username = "formuser"
        val password = "password123"
        val successResponse = LoginResponse(
            success = true,
            token = "token123",
            message = null,
            userId = "user123",
            username = null // No username from server
        )
        
        coEvery { mockUseCase.execute(username, password) } returns successResponse
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockSessionManager.saveUserSession("token123", username, "user123") }
        verify { mockView.navigateToHome() }
    }
    
    @Test
    fun `GIVEN failed login response WHEN login is called THEN should show error message`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "wrongpassword"
        val failureResponse = LoginResponse(
            success = false,
            token = null,
            message = "Invalid credentials",
            userId = null,
            username = null
        )
        
        coEvery { mockUseCase.execute(username, password) } returns failureResponse
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockView.showLoginError("Invalid credentials") }
        verify(exactly = 0) { mockSessionManager.saveUserSession(any(), any(), any()) }
        verify(exactly = 0) { mockView.navigateToHome() }
    }
    
    @Test
    fun `GIVEN success true but null token WHEN login is called THEN should show error`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val responseWithoutToken = LoginResponse(
            success = true,
            token = null, // Token is null
            message = "Success but no token",
            userId = "user123",
            username = username
        )
        
        coEvery { mockUseCase.execute(username, password) } returns responseWithoutToken
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockView.showLoginError("Success but no token") }
        verify(exactly = 0) { mockSessionManager.saveUserSession(any(), any(), any()) }
        verify(exactly = 0) { mockView.navigateToHome() }
    }
    
    @Test
    fun `GIVEN success true but empty token WHEN login is called THEN should show error`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val responseWithEmptyToken = LoginResponse(
            success = true,
            token = "", // Empty token
            message = "Empty token",
            userId = "user123",
            username = username
        )
        
        coEvery { mockUseCase.execute(username, password) } returns responseWithEmptyToken
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockView.showLoginError("Empty token") }
        verify(exactly = 0) { mockSessionManager.saveUserSession(any(), any(), any()) }
        verify(exactly = 0) { mockView.navigateToHome() }
    }
    
    @Test
    fun `GIVEN response with null message WHEN login fails THEN should show default error`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "wrongpassword"
        val responseWithNullMessage = LoginResponse(
            success = false,
            token = null,
            message = null, // Null message
            userId = null,
            username = null
        )
        
        coEvery { mockUseCase.execute(username, password) } returns responseWithNullMessage
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockView.showLoginError("Error desconocido") }
    }
    
    @Test
    fun `GIVEN network exception WHEN login is called THEN should show connection error`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val networkException = Exception("Network timeout")
        
        coEvery { mockUseCase.execute(username, password) } throws networkException
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockView.showLoginError("Network timeout") }
        verify(exactly = 0) { mockSessionManager.saveUserSession(any(), any(), any()) }
        verify(exactly = 0) { mockView.navigateToHome() }
    }
    
    @Test
    fun `GIVEN exception with null message WHEN login is called THEN should show default connection error`() = runTest {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val exceptionWithoutMessage = Exception(null as String?)
        
        coEvery { mockUseCase.execute(username, password) } throws exceptionWithoutMessage
        
        // WHEN
        presenter.login(username, password)
        advanceUntilIdle()
        
        // THEN
        verify { mockView.showLoading() }
        verify { mockView.hideLoading() }
        verify { mockView.showLoginError("Error de conexión") }
    }
    
    @Test
    fun `GIVEN presenter attached to view WHEN attachView is called THEN should attach view`() {
        // GIVEN
        val newMockView: LoginContract.View = mockk(relaxed = true)
        
        // WHEN
        presenter.attachView(newMockView)
        
        // THEN
        // No exception should be thrown, view should be attached
        // We can verify this indirectly by testing login with the new view
        presenter.login("", "")
        verify { newMockView.showLoginError(any()) }
    }
    
    @Test
    fun `GIVEN presenter with attached view WHEN detachView is called THEN should detach view`() {
        // GIVEN - presenter already has mockView attached
        
        // WHEN
        presenter.detachView()
        
        // THEN
        // After detaching, login should not call view methods (won't crash but won't call methods)
        presenter.login("user", "pass")
        // We can't easily test this without changing the presenter implementation
        // This test ensures detachView doesn't throw exceptions
    }
    
    @Test
    fun `GIVEN presenter with job running WHEN unbindView is called THEN should cancel job and detach view`() {
        // GIVEN - presenter potentially has coroutines running
        
        // WHEN
        presenter.unbindView()
        
        // THEN
        // This should cancel any running coroutines and detach the view
        // The test ensures unbindView doesn't throw exceptions
    }
}